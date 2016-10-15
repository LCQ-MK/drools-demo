package com.drools.utils;

import com.drools.entity.Message;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by LANCHUNQIAN on 2016/10/14.
 */
public class Drools6toDB {

    public static void main(String[] args) {

        String rule = "package com.drools.org.drools.rules1\r\n";
        rule += "import com.drools.entity.Message;\r\n";
        rule += "rule \"Hello World\"\r\n";
        rule += "\twhen\r\n";
        rule += "m : Message( status == Message.HELLO, myMessage : message )";
        rule += "\tthen\r\n";
        rule += "\t\tSystem.out.println( myMessage );\r\n";
        rule += "\t\tm.setMessage(\"Goodbye cruel world\");\r\n";
        rule += "\t\tm.setStatus( Message.GOODBYE );;\r\n";
        rule += "\t\tupdate( m );;\r\n";
        rule += "end\r\n";
        rule += "rule \"GoodBye\"\r\n";
        rule += "\twhen\r\n";
        rule += "Message( status == Message.GOODBYE, myMessage : message )";
        rule += "\tthen\r\n";
        rule += "\t\tSystem.out.println( 2+\":\"+myMessage );\r\n";
        rule += "end\r\n";

        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/org/drools/rules1.drl",rule.getBytes());

        KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();
        Results results = kieBuilder.getResults();
        if( results.hasMessages( org.kie.api.builder.Message.Level.ERROR ) ){
            System.out.println( results.getMessages() );
            throw new IllegalStateException( "### errors ###" );
        }
        KieContainer kieContainer = kieServices.newKieContainer( kieServices.getRepository().getDefaultReleaseId() );
        KieBase kieBase = kieContainer.getKieBase();
        KieSession ksession = kieBase.newKieSession();
        Message message = new Message();
        message.setMessage("Hello World");
        message.setStatus(Message.HELLO);

        Message message2 = new Message();
        message2.setStatus(Message.HELLO);
        message2.setMessage("hi world!");
        ksession.addEventListener( new DebugAgendaEventListener() );
        ksession.addEventListener( new DebugRuleRuntimeEventListener() );
        ksession.insert(message);
        ksession.insert(message2);
        ksession.fireAllRules();
    }
}
