package pl.kmiecik.holistek.email.examples.james;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class Read {
    public static void main(String[] args) throws Exception {
        String host = "james.local";
        String username = "user01@james.local";
        String password = "1234";

       /* String host = "eoltserverprod.local";
        String username = "mail@eoltserverprod.local";
        String password = "sretset";*/

        Properties properties = new Properties();
        properties.put("mail.store.host", host);

        Session session = Session.getDefaultInstance(properties, null);

        Store store = session.getStore("imap");
        store.connect(host, username, password);
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        // search for all "unseen" messages
        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
        Message messages[] = folder.search(unseenFlagTerm);

        // retrieve ALL messages from the folder in an array and print it
        //Message[] messages = emailFolder.getMessages();
        System.out.println("messages.length---" + messages.length);

        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            folder.setFlags(new Message[]{message}, new Flags(Flags.Flag.SEEN), true); //change flag to "seen mail"
            System.out.println("---------------------------------");
            writePart(message);
            String line = reader.readLine();
            if ("YES".equals(line)) {
                message.writeTo(System.out);
            } else if ("QUIT".equals(line)) {
                break;
            }
        }

        folder.close(false);
        store.close();

    }

    /*
     * This method checks for content-type
     * based on which, it processes and
     * fetches the content of the message
     */
    public static void writePart(Part p) throws Exception {
        if (p instanceof Message)
            //Call methos writeEnvelope
            writeEnvelope((Message) p);

        System.out.println("----------------------------");
        System.out.println("CONTENT-TYPE: " + p.getContentType());

        //check if the content is plain text
        if (p.isMimeType("text/plain")) {
            System.out.println("This is plain text");
            System.out.println("---------------------------");
            System.out.println((String) p.getContent());
        }

    }

    /*
     * This method would print FROM,TO and SUBJECT of the message
     */
    public static void writeEnvelope(Message m) throws Exception {
        System.out.println("This is the message envelope");
        System.out.println("---------------------------");
        Address[] a;

        // FROM
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++)
                System.out.println("FROM: " + a[j].toString());
        }

        // TO
        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++)
                System.out.println("TO: " + a[j].toString());
        }

        // SUBJECT
        if (m.getSubject() != null)
            System.out.println("SUBJECT: " + m.getSubject());

    }

}
