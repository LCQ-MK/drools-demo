package com.drools.utils;

import com.drools.entity.Message;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * Created by LANCHUNQIAN on 2016/10/18.
 */
public class DRLTest {

    public static void main(String str[]) {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add( ResourceFactory.newFileResource("src/main/resources/org/drools/test.drl"), ResourceType.DRL );
        if ( kbuilder.hasErrors() ) {
            System.err.println( kbuilder.getErrors().toString() );
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

        Message message = new Message("Hello World", Message.HELLO);

        ksession.insert(message);

        ksession.fireAllRules();
        ksession.dispose();
    }
}
