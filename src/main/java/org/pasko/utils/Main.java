package org.pasko.utils;

import java.util.Collection;
import org.pasko.utils.strings.FileLoader;
import org.pasko.utils.client.ClientBuilder;
import org.pasko.utils.client.MailClient;
import org.pasko.utils.client.Worker;
import org.pasko.utils.client.rules.Content;
import org.pasko.utils.client.rules.SentFrom;
import org.pasko.utils.client.rules.SentTo;
import org.pasko.utils.client.rules.StringRule;
import org.pasko.utils.client.rules.Title;

/**
 * Hello world!
 *
 */
public class Main {
    private static StringRule spamKeywordsContentRule;
    private static StringRule spamKeywordsTitleRule;

    public static void main(String[] args) {
        defaultConfiguration();
    }

    private static void defaultConfiguration() {
        final Collection<String> spamKeywords = new FileLoader("spam_keywords.txt").getList();
        spamKeywordsContentRule = new Content().containsOne(spamKeywords).moveTo("SPAM");
        spamKeywordsTitleRule = new Title().containsOne(spamKeywords).moveTo("SPAM");
        performCleaningMainMailAccountOverImap();
    }

    private static void performCleaningMainMailAccountOverImap() {
        MailClient paskoOverImap = ClientBuilder.createClient()
                .forUser("***@op.pl")
                .usingPassword("*******")
                .connectingWith("imap.poczta.onet.pl")
                .atPort(993)
                .usingProtocol("SSL")
                .byImap()
                .done();
        paskoOverImap.connect();
        Worker work = new Worker();
        
        work.addRule(new SentTo().contains("ytkownicy_OnetPoczty").moveTo("onet"));
        work.addRule(new Content().contains("List został wysłany za zgodą użytkowników OnetPoczty").moveTo("onet"));
        work.addRule(new SentFrom().contains("facebookmail").moveTo("facebook"));
        
        work.addRule(new SentFrom().contains("affiliate-24.com").moveTo("SPAM"));
        work.addRule(new SentFrom().contains("imailingo.com").moveTo("SPAM"));
        work.addRule(new SentFrom().contains("globlotto.com").moveTo("SPAM"));
        
        work.addRule(new Title().containsRegex("Wydzia.{1,8} ta.{1,8}ca").moveTo("taniec"));
        work.addRule(new Content().containsRegex("Wydzia.{1,9} ta.{1,8}ca").moveTo("taniec"));
        work.addRule(new Content().contains("wydzialtanca").moveTo("taniec"));
        work.addRule(new SentFrom().containsRegex("wydzial(-|_)?tanca").moveTo("taniec"));
        work.addRule(spamKeywordsTitleRule);
        work.addRule(spamKeywordsContentRule);
        work.doJob(paskoOverImap);
        paskoOverImap.flush();
    }
}
