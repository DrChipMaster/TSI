﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace teste2
{

    public partial class Form1 : Form
    {

        string[] lines;
        string path;
        private Report report;
        int linesnumber = 0;
        int switchLong = 0;
        public Form1(string path)
        {
            report = new Report(path);
            InitializeComponent();
            this.path = path;
            try
            {
                this.lines = new string[2024];
                int spacecount = 0;
                int lastspacecount = 0;
                using (StreamReader sr = new StreamReader(path.ToString()))
                {
                    bool existTabs = false;
                    string text = "";
                    while (!sr.EndOfStream)
                    {
                        lines[linesnumber] = sr.ReadLine();
                        richTextBox1.AppendText(linesnumber + ": ");
                        richTextBox1.AppendText(lines[linesnumber]);
                        richTextBox1.AppendText("\r\n");
                       
                        linesnumber++;

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


        private void checkSwitchCasesSize()
        {
            int i = 0;
            int numbLines = 0;
            int numbCases = 0;
            int index;
            for (i = 0; i < linesnumber; i++) { 
                if(lines[i].Contains("switch")){
                    //find last switch case end
                    index = i++;
                    int cycles = 0;
                    while (!lines[index].Contains("switch"))
                    {
                        if(lines[index].Contains("break;"))
                            numbCases++;
                        index++;
                    }
                    while(cycles < numbCases)
                    {
                        while (!lines[i].Contains("case"))
                        {
                            i++;
                        }
                        i++;
                        while (!lines[i].Contains("break;"))
                        {
                            numbLines++;
                        }
                        if (numbLines > 12)
                        {
                            numbCases++;
                            i = i + numbLines;
                            numbLines = 0;
                            switchLong = 1;
                        }
                        else
                        {
                            numbCases++;
                            i = i + numbLines;
                            numbLines = 0;
                            switchLong = 0;
                        }
                    }
                    i = index;
                }
            }
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

                report.tabReport.AppendText("Contains " + tabNumber + " TABS in lines: " + i + "\r\n");

            }
            return tabNumber;


        }

        private int checkspaces(int line)
        {
            int spacecount = 0;
            bool loop = true;
            int counter = 0;
            while (loop)
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
        private bool checkComments(char char1, char char2)
        {
            return (char1 == '/' && char2 == '/');
        }


        private bool checkBraces(int line)
        {
            int counterChar = 0;
            if (lines[line].Contains("{") || lines[line].Contains("}"))
                while (counterChar < lines[line].Length - 1)
                {
                    if (lines[line].ToCharArray()[counterChar] == '/')
                        if (lines[line].ToCharArray()[counterChar+1] == '/')
                            return false;
                    if (lines[line].ToCharArray()[counterChar] != ' ' && lines[line].ToCharArray()[counterChar] != (char)9)
                        if (lines[line].ToCharArray()[counterChar] != '}')
                        {
                            report.bracketUseReport.AppendText("brackets use inappropriate line:" + line+"\r\n");
                            return true;}
                            
                    counterChar++;
                }
            return false;
        }

        /* Linha a linha e' verificado */
        private void checkUnusedVariables()
        {
            /* also include enums, structs?? */
            string[] varTypes = {
                "sbyte", "short", "int", "long", "byte", "ushort",
                "uint", "ulong", "char", "float", "double", "decimal", "bool"};




   

        

    }

        private void Button1_Click(object sender, EventArgs e)
        {
           
            report.Show();
            report.tabReport.Text = "";
            report.spaceCountReport.Text = "";
            report.bracketUseReport.Text = "";
            richTextBox1.Text = "";
            report.inversionReport.Text = "";
            report.commaReport.Text = "";
            report.definesReport.Text = "";
            report.commentsReport.Text = "";
            report.bracketUseReport.Visible = false;
            report.inversionReport.Visible = false;
            report.inversionLabel.Visible = false;
            report.commaReport.Visible = false;
            report.definesReport.Visible = false;
            report.spaceCountReport.Visible = false;
            report.tabReport.Visible = false;
            report.commentsReport.Visible = false;
            report.commaLabel.Visible = false;
            report.definesLabel.Visible = false;
            report.spaceLabel.Visible = false;
            report.tabLabel.Visible = false;
            report.commentsLabel.Visible = false;
            report.bracketLabel.Visible = false;

            



            if (checkForBracketUse.Checked)
            {
                report.bracketUseReport.Visible = true;
                report.bracketLabel.Visible = true;

            }
            if(checkForComma.Checked)
            {
                report.commaReport.Visible = true;
                report.commaLabel.Visible = true;
            }
            if(checkForComments.Checked)
            {
                report.commentsReport.Visible = true;
                report.commentsLabel.Visible = true;
                //report.repot.Visible;
            }
            if(checkForDefines.Checked)
            {
                report.definesReport.Visible= true;
                report.definesLabel.Visible = true;
            }
            if(checkForSpaceCount.Checked)
            {
                report.spaceCountReport.Visible= true;
                report.spaceLabel.Visible = true;
            }
            if(checkForTabs.Checked)
            {
                report.tabReport.Visible = true;
                report.tabLabel.Visible= true;
            }
            if(checkInversion.Checked)
            {
                report.inversionLabel.Visible = true;
                report.inversionReport.Visible = true;
            }


            //Thread thr = new Thread(new ThreadStart(doScan));
            //thr.Start();
            










        }
        private void doScan()
        {
            this.lines = new string[1000];
            int spacecount = 0;
            int lastspacecount = 0;
            // Open the text file using a stream reader.

            using (StreamReader sr = new StreamReader(path.ToString()))
            {
                int linesnumber = 0;
                bool existTabs = false;
                string text = "";
                while (!sr.EndOfStream)
                {
                    lines[linesnumber] = sr.ReadLine();
                    text += lines[linesnumber];

                    linesnumber++;
                }

                int block = 0;
                for (int j = 0; j < linesnumber; j++)
                {
                    richTextBox1.SelectionFont = new Font("Times New Roman", 10, FontStyle.Regular);
                    richTextBox1.AppendText(j + ":    ");
                    int counter = 0;
                    Color color = Color.Red;
                    bool error = false, errorSpace = false, errorComment = false, errorBrackets = false, errorDefine = false;
                    int lenghDefine = 0;
                    // check for things section
                    if (checkForTabs.Checked)
                        if (checkTabsNumber(j) > 0) existTabs = true;
                    spacecount = checkspaces(j);

                    if (j > 0)
                    {


                        if (checkForSpaceCount.Checked)
                        {
                            if (lines[j].Contains('}')) block--;
                            if (spacecount != (block * 4))
                            {

                                if (spacecount != 0)
                                    report.spaceCountReport.AppendText("line: " + j + " com :" + spacecount + " espaços Suposto:" + block * 4 + "\r\n");
                                errorSpace = true;

                            }
                            if (lines[j].Contains('{'))
                            {
                                block++;
                            }
                        }


                        if (checkForDefines.Checked)
                        {
                            if (lines[j].Contains("#define "))
                            {
                                string[] splited = lines[j].Split(' ');
                                lenghDefine = splited[1].Length;
                                for (int k = 0; k < splited[1].Length; k++)
                                {
                                    if (((int)splited[1].ToCharArray()[k] > 96) && ((int)splited[1].ToCharArray()[k] < 123))
                                    {
                                        errorDefine = true;
                                        report.definesReport.AppendText("Contains bad define at line:" + j + "\r\n");
                                        break;
                                    }
                                }
                            }
                        }


                    }
                    if (checkForBracketUse.Checked)

                        errorBrackets = checkBraces(j);


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
                        if (errorSpace && counter != 0 && c != ' ')
                        {

                            errorSpace = false;
                        }

                        if ((errorSpace && c == ' ') || (errorSpace && counter == 0))
                        {
                            error = true;
                            color = Color.Green;
                        }
                        int aux1 = counter + 1;
                        char nextc;
                        if (errorComment)
                        {
                            errorComment = false;
                            error = true;
                            color = Color.Brown;

                        }

                        if (aux1 < lines[j].ToCharArray().Length)
                        {
                            nextc = lines[j].ToCharArray()[aux1];
                            if (c == ',' && nextc != ' ' && checkForComma.Checked)
                            {

                                report.commaReport.AppendText("Found bad use of comma at line" + j + "at character: " + counter + "\r\n");
                                error = true;
                                color = Color.Blue;
                            }
                            if (checkForComments.Checked)
                                if (checkComments(c, nextc))
                                {
                                    report.commentsReport.AppendText("Found bad use of Comments at line:" + j + "\r\n");

                                    error = true;
                                    color = Color.Brown;
                                    errorComment = true;
                                }
                        }
                        if (errorBrackets && (c == '{' || c == '}'))
                        {
                            error = true;
                            color = Color.Yellow;
                        }

                        if (errorDefine && counter > 7 && counter < lenghDefine + 8)
                        {
                            error = true;
                            color = Color.Purple;

                        }
                        if (c == '!')
                            if (verify_invertion(j, counter))
                            {

                                error = true;
                                color = Color.RosyBrown;
                                report.inversionReport.AppendText("Contains bad inversion at line:" + j + "\r\n");

                            }
                        //chamar aqui!!









                        if (error)
                        {

                            richTextBox1.SelectionFont = new Font("Times New Roman", 20, FontStyle.Underline);
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



                    //checkUnusedVariables
                    //if( line.)

                }



            }
        }

        private void richTextBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private bool verify_invertion (int line, int positionChar)
        {
            int count = 0;
            for(int i =0; i< lines[line].Length; i++)
            {
                if (lines[line].ToCharArray()[i] == '"')
                    {
                        if (lines[line].ToCharArray()[i-1] != '\\')
                            count++;
                    }
                else if (lines[line].ToCharArray()[i] == '!' && i< lines[line].Length-1)
                    {
                    if ((count%2) == 0 )
                        if (lines[line].ToCharArray()[i + 1] != '=')
                            if (positionChar == i)
                                return true;
                    }
            }
            return false;
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


