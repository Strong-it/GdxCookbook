package com.libgdx.cookbook.chp05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * The XML parsing primer
 * 确保XML文件是UTF-8，这样libgdx才会无误的解析
 *
 */
public class XmlParsingSample extends BaseScreen {

    @Override
    public void show() {
        try {
            // 可以参考InputProfile.java文件，里面也有一个解析XML的测试用例
            XmlReader reader = new XmlReader();
            Element root = reader.parse(Gdx.files.internal("data/credits.xml")); // 解析XML文件要有一个XmlReader的实例，然后调用parse方法
            //System.out.println(root);                                            // 加载的整个XML文件
            //System.out.println("root name: " + root.getName()); // Credits
            //System.out.println("root test: " + root.getText()); // null
            System.out.println("child num: " + root.getChildCount());
            
            System.out.println("=========");
            System.out.println("Book data");
            System.out.println("=========");
            
            Element bookElement = root.getChildByName("Book"); // <Book year="2014" pages="300" >Libgdx Game Development Cookbook</Book>
            
            System.out.println("Name:" + bookElement.getName());
            System.out.println("Title: " + bookElement.getText());
            System.out.println("Year: " + bookElement.getInt("year")); // Attributes 通过属性名称来访问
            System.out.println("Number of pages: " + bookElement.getInt("pages"));
            
            
//            <Authors>
//            <Author>David Saltares Márquez</Author>
//            <Author>Alberto Cejas Sánchez</Author>
//            </Authors>
            Array<Element> authors = root.getChildrenByNameRecursively("Author");  // 递归遍历，否则的话返回null
            /** 下面的方法也能遍历出author来
            Element authorsElement = root.getChildByName("Authors");
            int childNum = authorsElement.getChildCount();
            for (int i = 0; i < childNum; i++) {
                System.out.println("  *  " + authorsElement.getChild(i).getText());
            }
            */
            
            System.out.println("Authors: ");
           
            for (Element author : authors) {
                System.out.println("  *  " + author.getText());
            }
            
            Array<Element> reviewers = root.getChildrenByNameRecursively("Reviewer");
            
            System.out.println("Reviewers: ");
            
            for (Element reviewer : reviewers) {
                System.out.println("  * " + reviewer.getText());
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Gdx.app.exit();
    }

}
