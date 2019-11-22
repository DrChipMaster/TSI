using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace teste2
{
   
    public partial class Form1 : Form
    {
  
        string[] lines;
        
        public Form1()
        {
           lines = new string[1000];
            int spacecount = 0;
            int lastspacecount = 0;
            InitializeComponent();
            try
            {   // Open the text file using a stream reader.
                //using (StreamReader sr = new StreamReader("Form1.cs"))
                using (StreamReader sr = new StreamReader("main.c"))
                {
                    int linesnumber = 0;
                    bool existTabs = false;
                    while (!sr.EndOfStream)
                    {
                        lines[linesnumber] = sr.ReadLine();
                        linesnumber++;
                    }
                    int block = 0;
                    for (int j = 0; j < linesnumber; j++)
                    {
                        richTextBox1.SelectionFont = new Font("Times New Roman", 10, FontStyle.Regular);
                        richTextBox1.AppendText(j + ":    ");
                        int counter = 0;
                        Color color = Color.Red;
                        bool error = false, errorSpace= false;
                        // check for things section
                        if (checkTabsNumber(j) > 0) existTabs = true;
                        spacecount = checkspaces(j);

                        if(j>0)
                        {
                           
                            if (spacecount != (block*4))
                                {
                              
                                if(spacecount!=0)
                                    textBox2.AppendText( "line: "+ j +" com :"+spacecount+" espaços Suposto:"+ block*4+"\r\n");
                                    errorSpace = true;

                                }
                            if(lines[j].Contains('{'))
                            {
                                block++;
                            }
                            if (lines[j].Contains('}')) block--;

                          
                        }


                        // check for charactes section
                      
                        while (counter < lines[j].Length)
                        {
                            char c = lines[j].ToCharArray()[counter];

                            if (existTabs)
                            {
                                Color aux;
                                if ((aux = checkTabs(c)) != Color.Pink)
                                {
                                    error = true;
                                    color = aux;
                                    
                                }
                            }
                            if (errorSpace && counter != 0 && c!=' ')
                            {
                               
                                errorSpace = false;
                            }

                            if ((errorSpace && c==' ') || (errorSpace && counter ==0))
                                {
                                error = true;
                                color = Color.Green;
                            }
                            int aux1 = counter + 1;
                            char nextc;
                            if (aux1 < lines[j].ToCharArray().Length)
                            {
                                nextc= lines[j].ToCharArray()[aux1];
                                if (c == ',' && nextc != ' ')
                                {
                                    error = true;
                                    color = Color.Blue;

                                }
                            }

                          
                             





                            if (error)
                            {
                                
                                richTextBox1.SelectionFont = new Font("Times New Roman", 10, FontStyle.Underline);
                                richTextBox1.AppendText(c.ToString(), color);
                                
                                error = false;
                                color = Color.Black;


                                }
                                else
                            {
                                richTextBox1.SelectionFont = new Font("Times New Roman", 10, FontStyle.Regular);
                                richTextBox1.AppendText(lines[j].ToCharArray()[counter].ToString());
                            }
                            //if (haveerror)
                            //    errorSpace = true;

                            counter++;
                            
                        }
                        existTabs = false;
                        richTextBox1.AppendText("\r\n");
                       // checkTabs(j);

                    }



                }
            }
            catch (IOException a)
            {
                richTextBox1.Text = "The file could not be read:";
                richTextBox1.AppendText(a.Message);
            }



        }

        private void TextBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private Color checkTabs(char c)
        {

          
                if (c == (char)9)
                {

                return Color.Red;


                }
                else
                {
                return Color.Pink;

                }



            // checkBox1.AutoCheck = true;
            // textBox2
            //  string tabs = lines[i].Split()



            

        }

    


        
        private int checkTabsNumber(int i)
        {
            int tabNumber = 0;

            if (lines[i].Contains((char)9))
            {
                int counter = 0;


                while (counter < lines[i].Length)
                {
                    if (lines[i].ToCharArray()[counter] == (char)9)
                    {
                        tabNumber++;
                    }
                     counter++;
                }
              
                textBox2.AppendText("Contains " + tabNumber + " TABS in lines: " + i + "\r\n");

            }
            return tabNumber;


        }

        private int checkspaces(int line)
        {

            int  spacecount = 0;
            bool loop= true;
            int counter=0;
            while(loop)
            {
                if (counter < lines[line].Length)
                {
                    if (lines[line].ToCharArray()[counter] == ' ' && counter < lines[line].Length)
                    {
                        spacecount++;
                    }
                    else loop = false;
                }
                else loop = false;
                counter++;


            }
            return spacecount;
            
        }


       

    }



    public static class RichTextBoxExtensions
    {
        public static void AppendText(this RichTextBox box, string text, Color color)
        {
            box.SelectionStart = box.TextLength;
            box.SelectionLength = 0;

            box.SelectionColor = color;
            box.AppendText(text);
            box.SelectionColor = box.ForeColor;
        }
    }
}
