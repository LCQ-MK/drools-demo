package com.drools.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.drools.lang.DrlDumper;
import org.drools.lang.api.DescrFactory;
import org.drools.lang.descr.PackageDescr;

/**
 * Created by LANCHUNQIAN on 2016/10/17.
 */
public class DRLCreater {

    public static void main(String str[]){
        PackageDescr pkg = DescrFactory.newPackage()
                .name("org.drools.example")
                .newImport()
                    .target("com.drools.entity.Message")
                .end()
                .newRule().name("Hello World")
                .lhs()
                    .and()
                        .pattern("Message").id( "$message", false ).constraint("status == Message.HELLO").constraint("printMsg : message").end()
                    .end()
                .end()
                .rhs(" \tSystem.out.println(\"\\nHello World -------------------------------------\");\n" +
                        "\t//系统输出和重新设置实体属性\n" +
                        "\tSystem.out.println(\"rule \\\"Hello World printMsg\\\": \" + printMsg );\n" +
                        "\n" +
                        " \tMessage message2=new Message(\"rule \\\"Hello World printMsg\\\": I am message2 and I an created in rule Hello World!\", 2);\n" +
                        "\tSystem.out.println(message2.getMessage());\n" +
                        "\n" +
                        "\t$message.setMessage( \"Goodbye~\" );\n" +
                        "\t$message.setStatus( Message.GOODBYE );\n" +
                        "\t//更新实体，会重新触发规则\n" +
                        "\tupdate( $message );\n")
                .end()
                .newImport()
                    .target("com.drools.entity.People")
                .end()
                .newRule().name("GoodBye")
                    .lhs()
                        .and()
                            .pattern("Message").id("$message",false).constraint("status == Message.GOODBYE").constraint("printMsg : message").end()
                        .end()
                    .end()
                .rhs("\tSystem.out.println(\"\\nGoodBye -----------------------------------------\");\n" +
                        "\tSystem.out.println(\"rule \\\"GoodBye printMsg\\\": \" + printMsg);\n "
                    )
                .end()
                .end()
                .getDescr();
        DrlDumper dumper=new DrlDumper();
        String drl=dumper.dump(pkg);
        System.out.print(drl);
        try{
            // create new file
            File file = new File("src/main/resources/org/drools/test.drl");
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(drl);
            // close connection
            bw.close();
            System.out.println("File Created Successfully");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}

