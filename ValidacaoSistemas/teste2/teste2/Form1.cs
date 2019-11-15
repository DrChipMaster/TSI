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
            InitializeComponent();
            try
            {   // Open the text file using a stream reader.
                using (StreamReader sr = new StreamReader("process_commands.c"))
                {
                    int i = 0;

                    while (!sr.EndOfStream)
                    {
                        lines[i] = sr.ReadLine();
                      
                        if(lines[i].Contains((char)9))
                        {
                            int tabNumber=0;
                            int counter=0;
                            while(counter < lines[i].Length)
                            {
                                if (lines[i].ToCharArray()[counter] == (char)9)
                                {
                                    tabNumber++;
                                }
                                counter++;
                            }
                            // checkBox1.AutoCheck = true;
                            // textBox2
                          //  string tabs = lines[i].Split()
                            richTextBox1.AppendText(i + ":   " + lines[i] + "\r\n");

                            textBox2.AppendText("Contains "+ tabNumber +" TABS in lines: "+ i + "\r\n");

                        }
                        else
                        {
                            richTextBox1.AppendText(i + ":   " + lines[i] + "\r\n");
                        }
                        i++;

                    }


                    
                }
            }
            catch (IOException a)
            {
                richTextBox1.Text="The file could not be read:";
                richTextBox1.AppendText(a.Message);
            }



        }

        private void TextBox1_TextChanged(object sender, EventArgs e)
        {
           
        }
    }
}
