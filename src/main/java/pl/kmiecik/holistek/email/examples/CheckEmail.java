package pl.kmiecik.holistek.email.examples;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Properties;

/**
 * https://www.tutorialspoint.com/javamail_api/javamail_api_checking_emails.htm
 */

public class CheckEmail {

    public static void check(String host, String storeType, String user,
                             String password)
    {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties,null);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // search for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            Message messages[] = emailFolder.search(unseenFlagTerm);

            // retrieve all  messages from the folder in an array and print it
         /*   Message[] messages = emailFolder.getMessages();*/
            System.out.println("messages.length---" + messages.length);



            if (messages.length == 0) System.out.println("No messages found.");


            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";

        String username = "holistech.gdansk@gmail.com";// change accordingly
        String password = "mp3!";// change accordingly
        check(host, mailStoreType, username, password);

    }



}
