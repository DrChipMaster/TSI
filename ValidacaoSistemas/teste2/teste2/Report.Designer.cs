namespace teste2
{
    partial class Report
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.tabReport = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.spaceCountReport = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.bracketUseReport = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // tabReport
            // 
            this.tabReport.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.tabReport.Location = new System.Drawing.Point(42, 50);
            this.tabReport.Multiline = true;
            this.tabReport.Name = "tabReport";
            this.tabReport.Size = new System.Drawing.Size(203, 222);
            this.tabReport.TabIndex = 0;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.BackColor = System.Drawing.Color.Transparent;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.label1.ForeColor = System.Drawing.Color.Orange;
            this.label1.Location = new System.Drawing.Point(101, 27);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(86, 20);
            this.label1.TabIndex = 1;
            this.label1.Text = "Tab report:";
            // 
            // spaceCountReport
            // 
            this.spaceCountReport.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.spaceCountReport.Location = new System.Drawing.Point(313, 50);
            this.spaceCountReport.Multiline = true;
            this.spaceCountReport.Name = "spaceCountReport";
            this.spaceCountReport.Size = new System.Drawing.Size(203, 222);
            this.spaceCountReport.TabIndex = 2;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.BackColor = System.Drawing.Color.Transparent;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.label2.ForeColor = System.Drawing.Color.Orange;
            this.label2.Location = new System.Drawing.Point(332, 27);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(159, 20);
            this.label2.TabIndex = 3;
            this.label2.Text = "Space Count Report:";
            // 
            // bracketUseReport
            // 
            this.bracketUseReport.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.bracketUseReport.Location = new System.Drawing.Point(572, 50);
            this.bracketUseReport.Multiline = true;
            this.bracketUseReport.Name = "bracketUseReport";
            this.bracketUseReport.Size = new System.Drawing.Size(203, 222);
            this.bracketUseReport.TabIndex = 4;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.BackColor = System.Drawing.Color.Transparent;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.label3.ForeColor = System.Drawing.Color.Orange;
            this.label3.Location = new System.Drawing.Point(589, 27);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(159, 20);
            this.label3.TabIndex = 5;
            this.label3.Text = "Brackect use Report:";
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(713, 415);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 6;
            this.button1.Text = "Export";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.Button1_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(632, 415);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 7;
            this.button2.Text = "Close";
            this.button2.UseVisualStyleBackColor = true;
            // 
            // Report
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackgroundImage = global::teste2.Properties.Resources.shattered_island;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.bracketUseReport);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.spaceCountReport);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.tabReport);
            this.Name = "Report";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Report";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        public  System.Windows.Forms.TextBox tabReport;
        public System.Windows.Forms.Label label1;
        public System.Windows.Forms.TextBox spaceCountReport;
        private System.Windows.Forms.Label label2;
        public System.Windows.Forms.TextBox bracketUseReport;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
    }
}