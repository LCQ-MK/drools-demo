package com.drools.utils;

import com.drools.entity.Message;
import com.drools.entity.People;
import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionWsaServerTube;
import org.drools.core.RuleBaseConfiguration;
import org.drools.core.WorkingMemory;
import org.drools.core.audit.event.RuleBaseLogEvent;
import org.drools.core.marshalling.impl.ProtobufMessages;
import org.drools.core.marshalling.impl.RuleBaseNodes;
import org.drools.core.rule.FactType;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.KnowledgeBaseFactory;

import java.util.ArrayList;

/**
 * Created by LANCHUNQIAN on 2016/10/13.
 */
public class test {

    public static void main(String[] args) {

        //从工厂中获得KieServices实例
        KieServices kieServices = KieServices.Factory.get();
        //从KieServices中获得KieContainer实例，其会加载kmodule.xml文件并load规则文件
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        //建立KieSession到规则文件的通信管道
        KieSession kSession = kieContainer.newKieSession("ksession-rules");

//        ProtobufMessages.KnowledgeBase knowledgeBase = KnowledgeBaseFactory().new
        FactType peopleType = kSession.getFactType();

        //初始化全局变量messageGlobal
        kSession.setGlobal("peopleGlobal",new ArrayList<People>());
        People people1 = new People("Lan", 12, 123);
        People people2 = new People("Chun", 22, 456);
        People people3 = new People("Qian", 32, 789);

        Message message = new Message("Hello World", Message.HELLO);

        //将实体类插入执行规则
        kSession.insert(message);
        kSession.insert(people1);
        kSession.insert(people2);
        kSession.insert(people3);

        kSession.fireAllRules();

        System.out.println("\nfinal message" + message.getMessage());
        System.out.println();

        //query的使用
        QueryResults results = kSession.getQueryResults( "people over the age of x", new Object[] {20, 456});
        for ( QueryResultsRow row : results ) {
            People person = (People) row.get( "people" );
            System.out.println("query people over the age of 20 " +  person);
        }
        System.out.println();

        //输出全局变量
        ArrayList<People> finalPeopleGlobal = (ArrayList<People>)kSession.getGlobal("peopleGlobal");
        for(int i = 0;i < finalPeopleGlobal.size();i ++){
            System.out.println("Age > 20 " + finalPeopleGlobal.get(i));
        }
    }
}
