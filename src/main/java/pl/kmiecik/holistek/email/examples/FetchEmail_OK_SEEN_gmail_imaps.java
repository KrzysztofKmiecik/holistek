package pl.kmiecik.holistek.email.examples;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

/**
 * https://www.tutorialspoint.com/javamail_api/javamail_api_fetching_emails.htm
 */

/*
/Users/kmk/.sdkman/candidates/java/11.0.10.hs-adpt/bin/java -javaagent:/Users/kmk/Library/Application Support/JetBrains/Toolbox/apps/IDEA-U/ch-0/212.5284.40/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=50779:/Users/kmk/Library/Application Support/JetBrains/Toolbox/apps/IDEA-U/ch-0/212.5284.40/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Volumes/RAM Disk/holistek/target/classes:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter-data-jpa/2.5.1/spring-boot-starter-data-jpa-2.5.1.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter-jdbc/2.5.1/spring-boot-starter-jdbc-2.5.1.jar:/Users/kmk/.m2/repository/com/zaxxer/HikariCP/4.0.3/HikariCP-4.0.3.jar:/Users/kmk/.m2/repository/org/springframework/spring-jdbc/5.3.8/spring-jdbc-5.3.8.jar:/Users/kmk/.m2/repository/jakarta/transaction/jakarta.transaction-api/1.3.3/jakarta.transaction-api-1.3.3.jar:/Users/kmk/.m2/repository/jakarta/persistence/jakarta.persistence-api/2.2.3/jakarta.persistence-api-2.2.3.jar:/Users/kmk/.m2/repository/org/hibernate/hibernate-core/5.4.32.Final/hibernate-core-5.4.32.Final.jar:/Users/kmk/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/Users/kmk/.m2/repository/org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar:/Users/kmk/.m2/repository/net/bytebuddy/byte-buddy/1.10.22/byte-buddy-1.10.22.jar:/Users/kmk/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/kmk/.m2/repository/org/jboss/jandex/2.2.3.Final/jandex-2.2.3.Final.jar:/Users/kmk/.m2/repository/com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar:/Users/kmk/.m2/repository/org/dom4j/dom4j/2.1.3/dom4j-2.1.3.jar:/Users/kmk/.m2/repository/org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar:/Users/kmk/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.4/jaxb-runtime-2.3.4.jar:/Users/kmk/.m2/repository/org/glassfish/jaxb/txw2/2.3.4/txw2-2.3.4.jar:/Users/kmk/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.12/istack-commons-runtime-3.0.12.jar:/Users/kmk/.m2/repository/org/springframework/data/spring-data-jpa/2.5.1/spring-data-jpa-2.5.1.jar:/Users/kmk/.m2/repository/org/springframework/data/spring-data-commons/2.5.1/spring-data-commons-2.5.1.jar:/Users/kmk/.m2/repository/org/springframework/spring-orm/5.3.8/spring-orm-5.3.8.jar:/Users/kmk/.m2/repository/org/springframework/spring-context/5.3.8/spring-context-5.3.8.jar:/Users/kmk/.m2/repository/org/springframework/spring-tx/5.3.8/spring-tx-5.3.8.jar:/Users/kmk/.m2/repository/org/springframework/spring-beans/5.3.8/spring-beans-5.3.8.jar:/Users/kmk/.m2/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar:/Users/kmk/.m2/repository/org/springframework/spring-aspects/5.3.8/spring-aspects-5.3.8.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter-thymeleaf/2.5.1/spring-boot-starter-thymeleaf-2.5.1.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter/2.5.1/spring-boot-starter-2.5.1.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot/2.5.1/spring-boot-2.5.1.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/2.5.1/spring-boot-autoconfigure-2.5.1.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter-logging/2.5.1/spring-boot-starter-logging-2.5.1.jar:/Users/kmk/.m2/repository/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar:/Users/kmk/.m2/repository/ch/qos/logback/logback-core/1.2.3/logback-core-1.2.3.jar:/Users/kmk/.m2/repository/org/apache/logging/log4j/log4j-to-slf4j/2.14.1/log4j-to-slf4j-2.14.1.jar:/Users/kmk/.m2/repository/org/apache/logging/log4j/log4j-api/2.14.1/log4j-api-2.14.1.jar:/Users/kmk/.m2/repository/org/slf4j/jul-to-slf4j/1.7.30/jul-to-slf4j-1.7.30.jar:/Users/kmk/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/Users/kmk/.m2/repository/org/yaml/snakeyaml/1.28/snakeyaml-1.28.jar:/Users/kmk/.m2/repository/org/thymeleaf/thymeleaf-spring5/3.0.12.RELEASE/thymeleaf-spring5-3.0.12.RELEASE.jar:/Users/kmk/.m2/repository/org/thymeleaf/thymeleaf/3.0.12.RELEASE/thymeleaf-3.0.12.RELEASE.jar:/Users/kmk/.m2/repository/org/attoparser/attoparser/2.0.5.RELEASE/attoparser-2.0.5.RELEASE.jar:/Users/kmk/.m2/repository/org/unbescape/unbescape/1.1.6.RELEASE/unbescape-1.1.6.RELEASE.jar:/Users/kmk/.m2/repository/org/thymeleaf/extras/thymeleaf-extras-java8time/3.0.4.RELEASE/thymeleaf-extras-java8time-3.0.4.RELEASE.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter-web/2.5.1/spring-boot-starter-web-2.5.1.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter-json/2.5.1/spring-boot-starter-json-2.5.1.jar:/Users/kmk/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.12.3/jackson-databind-2.12.3.jar:/Users/kmk/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.12.3/jackson-annotations-2.12.3.jar:/Users/kmk/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.12.3/jackson-core-2.12.3.jar:/Users/kmk/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.12.3/jackson-datatype-jdk8-2.12.3.jar:/Users/kmk/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.12.3/jackson-datatype-jsr310-2.12.3.jar:/Users/kmk/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.12.3/jackson-module-parameter-names-2.12.3.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter-tomcat/2.5.1/spring-boot-starter-tomcat-2.5.1.jar:/Users/kmk/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/9.0.46/tomcat-embed-core-9.0.46.jar:/Users/kmk/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/9.0.46/tomcat-embed-websocket-9.0.46.jar:/Users/kmk/.m2/repository/org/springframework/spring-web/5.3.8/spring-web-5.3.8.jar:/Users/kmk/.m2/repository/org/springframework/spring-webmvc/5.3.8/spring-webmvc-5.3.8.jar:/Users/kmk/.m2/repository/org/springframework/spring-expression/5.3.8/spring-expression-5.3.8.jar:/Users/kmk/.m2/repository/mysql/mysql-connector-java/8.0.25/mysql-connector-java-8.0.25.jar:/Users/kmk/.m2/repository/org/projectlombok/lombok/1.18.20/lombok-1.18.20.jar:/Users/kmk/.m2/repository/jakarta/xml/bind/jakarta.xml.bind-api/2.3.3/jakarta.xml.bind-api-2.3.3.jar:/Users/kmk/.m2/repository/jakarta/activation/jakarta.activation-api/1.2.2/jakarta.activation-api-1.2.2.jar:/Users/kmk/.m2/repository/org/springframework/spring-core/5.3.8/spring-core-5.3.8.jar:/Users/kmk/.m2/repository/org/springframework/spring-jcl/5.3.8/spring-jcl-5.3.8.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter-mail/2.5.1/spring-boot-starter-mail-2.5.1.jar:/Users/kmk/.m2/repository/org/springframework/spring-context-support/5.3.8/spring-context-support-5.3.8.jar:/Users/kmk/.m2/repository/com/sun/mail/jakarta.mail/1.6.7/jakarta.mail-1.6.7.jar:/Users/kmk/.m2/repository/com/sun/activation/jakarta.activation/1.2.2/jakarta.activation-1.2.2.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter-aop/2.5.1/spring-boot-starter-aop-2.5.1.jar:/Users/kmk/.m2/repository/org/springframework/spring-aop/5.3.8/spring-aop-5.3.8.jar:/Users/kmk/.m2/repository/org/aspectj/aspectjweaver/1.9.6/aspectjweaver-1.9.6.jar:/Users/kmk/.m2/repository/org/springframework/boot/spring-boot-starter-validation/2.5.1/spring-boot-starter-validation-2.5.1.jar:/Users/kmk/.m2/repository/org/apache/tomcat/embed/tomcat-embed-el/9.0.46/tomcat-embed-el-9.0.46.jar:/Users/kmk/.m2/repository/org/hibernate/validator/hibernate-validator/6.2.0.Final/hibernate-validator-6.2.0.Final.jar:/Users/kmk/.m2/repository/jakarta/validation/jakarta.validation-api/2.0.2/jakarta.validation-api-2.0.2.jar:/Users/kmk/.m2/repository/org/webjars/bootstrap/4.3.1/bootstrap-4.3.1.jar:/Users/kmk/.m2/repository/org/webjars/jquery/3.0.0/jquery-3.0.0.jar:/Users/kmk/.m2/repository/org/webjars/popper.js/1.14.3/popper.js-1.14.3.jar:/Users/kmk/.m2/repository/org/webjars/font-awesome/5.11.2/font-awesome-5.11.2.jar:/Users/kmk/.m2/repository/com/google/zxing/core/3.3.0/core-3.3.0.jar:/Users/kmk/.m2/repository/com/google/zxing/javase/3.3.0/javase-3.3.0.jar:/Users/kmk/.m2/repository/com/beust/jcommander/1.48/jcommander-1.48.jar:/Users/kmk/.m2/repository/com/github/jai-imageio/jai-imageio-core/1.3.1/jai-imageio-core-1.3.1.jar:/Users/kmk/.m2/repository/net/java/dev/jna/jna/5.7.0/jna-5.7.0.jar pl.kmiecik.holistek.email.examples.FetchEmail_OK_SEEN_gmail_imaps
messages.length---1
---------------------------------
This is the message envelope
---------------------------
FROM: HolisTechJobs <holistech@holistechweb.co.uk>
TO: przemyslaw.wojtas@aptiv.com
TO: holistech.gdansk@gmail.com
TO: Krzysztof.Kmiecik@aptiv.com
TO: arkadiusz.suliga@aptiv.com
SUBJECT: Calibration
----------------------------
CONTENT-TYPE: TEXT/PLAIN; charset=utf-8
This is plain text
---------------------------
FIXT_SMT_ICT1 (Gniazdo Meriva IP switch J4 SMT_ICT 1)
 */
public class FetchEmail_OK_SEEN_gmail_imaps {
    @Value("${proxyUser}")
    private String proxyUser;
    @Value("${proxyPassword")
    private String proxyPassword;

    public String getProxyUser() {
        return proxyUser;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    private void fetch(String myHost, String storeType, String user,
                             String password) {
        try {
            // create properties field
            Properties properties = getProperties(myHost);
            Session emailSession = Session.getDefaultInstance(properties);
            // emailSession.setDebug(true);

            // create the POP3 store object and connect with the pop server
            //     Store store = emailSession.getStore("pop3s");
            Store store = emailSession.getStore(storeType);

            store.connect(myHost, user, password);

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE); // orginally it was READ_ONLY but  it was changed due to  write "SEEN MAIL" flag

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // search for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            Message messages[] = emailFolder.search(unseenFlagTerm);


            // retrieve ALL messages from the folder in an array and print it
            //Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                emailFolder.setFlags(new Message[]{message}, new Flags(Flags.Flag.SEEN), true); //change flag to "seen mail"
                System.out.println("---------------------------------");
                writePart(message);
                //  String line = reader.readLine();
                //  if ("YES".equals(line)) {
                //   message.writeTo(System.out);
                //     } else if ("QUIT".equals(line)) {
                //        break;
                //   }
            }

            reader.close();
            // close the store and folder objects
            emailFolder.close();
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  Properties getProperties(String myHost) {

        MailSSLSocketFactory socketFactory = null;
        try {
            socketFactory = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        socketFactory.setTrustAllHosts(true);


        Properties properties = new Properties();
        if (myHost.equals("pop.gmail.com")) {
            properties.put("mail.store.protocol", "pop3");
            properties.put("mail.pop3.host", myHost);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
        }
        if (myHost.equals("imap.gmail.com")) {
            properties.put("mail.store.protocol", "imap");
            properties.put("mail.imap.host", myHost);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");//nie ma znaczenia
            properties.put("mail.imap.ssl.checkserveridentity", "false");
            properties.put("mail.imaps.ssl.trust", "*");
            properties.put("mail.imap.ssl.socketFactory", socketFactory);

            properties.put("mail.imap.proxy.host", "proxyrrus.delphiauto.net");
            //   properties.put("mail.imap.proxy.host", "http://autoproxy-amer.delphiauto.net");
            properties.put("mail.imap.proxy.port", "8080");
            properties.put("mail.imap.proxy.user",getProxyUser());
            properties.put("mail.imap.proxy.password", getProxyPassword());

        }


        return properties;
    }

    public static void main(String[] args) {

     /*   String username = "@gmail.com";// change accordingly
        String password = "";// change accordingly*/
        String username = "holistech.gdansk@gmail.com";// change accordingly
        String password = "mp3!";// change accordingly
        //POP robi lokalna kop ie
      /*  String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3s";*/


        //IMAP z serwera
        String host = "imap.gmail.com";// change accordingly
        String mailStoreType = "imaps"; //musi  byc imaps


        new FetchEmail_OK_SEEN_gmail_imaps().fetch(host, mailStoreType, username, password);


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
