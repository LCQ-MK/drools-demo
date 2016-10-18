package com.drools.utils;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Created by LANCHUNQIAN on 2016/10/18.
 */
public class DRLAdd {

    public static void main(String str[]){

        String drl = "rule \"Age Filter\"\n" +
                "    when\n" +
                "        $p : People(age > 20);\n" +
                "    then\n" +
                "        name = $p.getName();\n" +
                "        System.out.println(\"rule \\\"Age Filter printMsg\\\" 年龄大于20的人：\" + name);\n" +
                "     end";
        System.out.print(drl);
        try{
            FileWriter fw = new FileWriter("src/main/resources/org/drools/test.drl",true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(drl);
            bw.newLine();
            bw.flush();
            // close connection
            bw.close();
            System.out.println("File Add Successfully");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
