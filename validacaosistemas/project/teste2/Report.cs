using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace teste2
{
    public partial class Report : Form
    {
        string path;
        public Report(string files)
        {
            path = files;

            InitializeComponent();
        }

        private void Button1_Click(object sender, EventArgs e)
        {
            string rootFolder = path;
            int errortab;
            string text = tabReport.Text;
 

            rootFolder += "tabs.txt";
            File.Delete(rootFolder);
            System.IO.File.WriteAllText(rootFolder, text);
            errortab = File.ReadLines(rootFolder ).Count();
            text = spaceCountReport.Text;
            rootFolder = path + "Spaces.txt";
            File.Delete(rootFolder);
            System.IO.File.WriteAllText(rootFolder, text);
            int SpaceError = File.ReadLines(rootFolder).Count();
            text = bracketUseReport.Text;
            rootFolder = path + "bracket.txt";
            File.Delete(rootFolder);
            System.IO.File.WriteAllText(rootFolder, text);
            int bracketError = File.ReadLines(rootFolder).Count();
            text = inversionReport.Text;
            rootFolder = path + "inversionReport.txt";
            File.Delete(rootFolder);
            System.IO.File.WriteAllText(rootFolder, text);
            int InvError = File.ReadLines(rootFolder).Count();
            text = definesReport.Text;
            rootFolder = path + "definesReport.txt";
            File.Delete(rootFolder);
            System.IO.File.WriteAllText(rootFolder, text);
            int defError = File.ReadLines(rootFolder).Count();
            text = commaReport.Text;
            rootFolder = path + "commaReport.txt";
            File.Delete(rootFolder);
            System.IO.File.WriteAllText(rootFolder, text);
            int CommaError = File.ReadLines(rootFolder).Count();
            text = commentsReport.Text;
            rootFolder = path + "commentReport.txt";
            File.Delete(rootFolder);
            System.IO.File.WriteAllText(rootFolder, text);
            int CommentError = File.ReadLines(rootFolder).Count();
            text = " Found "+ errortab+ " BadSmells in Tabs Report\n\n"
               +" Found " + SpaceError + " BadSmells  in Space Report\n\n"
               + " Found " + bracketError + " BadSmells in bracket Report\n\n"
                + " Found " + InvError + " BadSmells in Invertion Report\n\n"
                + " Found " + defError + " BadSmells in Define Report\n\n"
                + " Found " + CommaError+ " BadSmells in Comma Report\n\n"
               + " Found " + CommentError + " BadSmells in Comment Report\n\n"
                + " Total  BadSmells = " + (errortab+SpaceError+bracketError+InvError+defError+CommaError+CommentError);
            rootFolder = path + "GENERALReport.txt";
            File.Delete(rootFolder);
            System.IO.File.WriteAllText(rootFolder, text);
            
        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Hide();
        }
    }
}
