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
            this.tabLabel = new System.Windows.Forms.Label();
            this.spaceCountReport = new System.Windows.Forms.TextBox();
            this.spaceLabel = new System.Windows.Forms.Label();
            this.bracketUseReport = new System.Windows.Forms.TextBox();
            this.bracketLabel = new System.Windows.Forms.Label();
            this.Export = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.definesLabel = new System.Windows.Forms.Label();
            this.definesReport = new System.Windows.Forms.TextBox();
            this.commaLabel = new System.Windows.Forms.Label();
            this.commaReport = new System.Windows.Forms.TextBox();
            this.commentsLabel = new System.Windows.Forms.Label();
            this.commentsReport = new System.Windows.Forms.TextBox();
            this.inversionLabel = new System.Windows.Forms.Label();
            this.inversionReport = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // tabReport
            // 
            this.tabReport.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.tabReport.Location = new System.Drawing.Point(42, 50);
            this.tabReport.Multiline = true;
            this.tabReport.Name = "tabReport";
            this.tabReport.ReadOnly = true;
            this.tabReport.Size = new System.Drawing.Size(203, 222);
            this.tabReport.TabIndex = 0;
            // 
            // tabLabel
            // 
            this.tabLabel.AutoSize = true;
            this.tabLabel.BackColor = System.Drawing.Color.Transparent;
            this.tabLabel.Font = new System.Drawing.Font("Monospac821 BT", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.tabLabel.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.tabLabel.Location = new System.Drawing.Point(92, 28);
            this.tabLabel.Name = "tabLabel";
            this.tabLabel.Size = new System.Drawing.Size(119, 19);
            this.tabLabel.TabIndex = 1;
            this.tabLabel.Text = "Tab report:";
            // 
            // spaceCountReport
            // 
            this.spaceCountReport.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.spaceCountReport.Location = new System.Drawing.Point(313, 50);
            this.spaceCountReport.Multiline = true;
            this.spaceCountReport.Name = "spaceCountReport";
            this.spaceCountReport.ReadOnly = true;
            this.spaceCountReport.Size = new System.Drawing.Size(203, 222);
            this.spaceCountReport.TabIndex = 2;
            // 
            // spaceLabel
            // 
            this.spaceLabel.AutoSize = true;
            this.spaceLabel.BackColor = System.Drawing.Color.Transparent;
            this.spaceLabel.Font = new System.Drawing.Font("Monospac821 BT", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.spaceLabel.ForeColor = System.Drawing.Color.DarkTurquoise;
            this.spaceLabel.Location = new System.Drawing.Point(328, 28);
            this.spaceLabel.Name = "spaceLabel";
            this.spaceLabel.Size = new System.Drawing.Size(199, 19);
            this.spaceLabel.TabIndex = 3;
            this.spaceLabel.Text = "Space Count Report:";
            // 
            // bracketUseReport
            // 
            this.bracketUseReport.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.bracketUseReport.Location = new System.Drawing.Point(572, 50);
            this.bracketUseReport.Multiline = true;
            this.bracketUseReport.Name = "bracketUseReport";
            this.bracketUseReport.ReadOnly = true;
            this.bracketUseReport.Size = new System.Drawing.Size(203, 222);
            this.bracketUseReport.TabIndex = 4;
            // 
            // bracketLabel
            // 
            this.bracketLabel.AutoSize = true;
            this.bracketLabel.BackColor = System.Drawing.Color.Transparent;
            this.bracketLabel.Font = new System.Drawing.Font("Monospac821 BT", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bracketLabel.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.bracketLabel.Location = new System.Drawing.Point(580, 28);
            this.bracketLabel.Name = "bracketLabel";
            this.bracketLabel.Size = new System.Drawing.Size(209, 19);
            this.bracketLabel.TabIndex = 5;
            this.bracketLabel.Text = "Brackect use Report:";
            // 
            // Export
            // 
            this.Export.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(128)))), ((int)(((byte)(0)))));
            this.Export.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.Export.Font = new System.Drawing.Font("Microsoft YaHei", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Export.Location = new System.Drawing.Point(1265, 605);
            this.Export.Name = "Export";
            this.Export.Size = new System.Drawing.Size(75, 23);
            this.Export.TabIndex = 6;
            this.Export.Text = "EXPORT";
            this.Export.UseVisualStyleBackColor = false;
            this.Export.Click += new System.EventHandler(this.Button1_Click);
            // 
            // button2
            // 
            this.button2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(128)))), ((int)(((byte)(0)))));
            this.button2.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.button2.Font = new System.Drawing.Font("Microsoft YaHei", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.button2.Location = new System.Drawing.Point(1184, 605);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 7;
            this.button2.Text = "CLOSE";
            this.button2.UseVisualStyleBackColor = false;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // definesLabel
            // 
            this.definesLabel.AutoSize = true;
            this.definesLabel.BackColor = System.Drawing.Color.Transparent;
            this.definesLabel.Font = new System.Drawing.Font("Monospac821 BT", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.definesLabel.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.definesLabel.Location = new System.Drawing.Point(71, 314);
            this.definesLabel.Name = "definesLabel";
            this.definesLabel.Size = new System.Drawing.Size(159, 19);
            this.definesLabel.TabIndex = 9;
            this.definesLabel.Text = "Defines Report:";
            this.definesLabel.Click += new System.EventHandler(this.label4_Click);
            // 
            // definesReport
            // 
            this.definesReport.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.definesReport.Location = new System.Drawing.Point(42, 337);
            this.definesReport.Multiline = true;
            this.definesReport.Name = "definesReport";
            this.definesReport.ReadOnly = true;
            this.definesReport.Size = new System.Drawing.Size(203, 222);
            this.definesReport.TabIndex = 8;
            // 
            // commaLabel
            // 
            this.commaLabel.AutoSize = true;
            this.commaLabel.BackColor = System.Drawing.Color.Transparent;
            this.commaLabel.Font = new System.Drawing.Font("Monospac821 BT", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.commaLabel.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.commaLabel.Location = new System.Drawing.Point(328, 314);
            this.commaLabel.Name = "commaLabel";
            this.commaLabel.Size = new System.Drawing.Size(199, 19);
            this.commaLabel.TabIndex = 11;
            this.commaLabel.Text = "Comma-Space Report:";
            // 
            // commaReport
            // 
            this.commaReport.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.commaReport.Location = new System.Drawing.Point(313, 337);
            this.commaReport.Multiline = true;
            this.commaReport.Name = "commaReport";
            this.commaReport.ReadOnly = true;
            this.commaReport.Size = new System.Drawing.Size(203, 222);
            this.commaReport.TabIndex = 10;
            // 
            // commentsLabel
            // 
            this.commentsLabel.AutoSize = true;
            this.commentsLabel.BackColor = System.Drawing.Color.Transparent;
            this.commentsLabel.Font = new System.Drawing.Font("Monospac821 BT", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.commentsLabel.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.commentsLabel.Location = new System.Drawing.Point(562, 314);
            this.commentsLabel.Name = "commentsLabel";
            this.commentsLabel.Size = new System.Drawing.Size(239, 19);
            this.commentsLabel.TabIndex = 13;
            this.commentsLabel.Text = "Use of comments Report:";
            // 
            // commentsReport
            // 
            this.commentsReport.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.commentsReport.Location = new System.Drawing.Point(572, 337);
            this.commentsReport.Multiline = true;
            this.commentsReport.Name = "commentsReport";
            this.commentsReport.ReadOnly = true;
            this.commentsReport.Size = new System.Drawing.Size(203, 222);
            this.commentsReport.TabIndex = 12;
            // 
            // inversionLabel
            // 
            this.inversionLabel.AutoSize = true;
            this.inversionLabel.BackColor = System.Drawing.Color.Transparent;
            this.inversionLabel.Font = new System.Drawing.Font("Monospac821 BT", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.inversionLabel.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.inversionLabel.Location = new System.Drawing.Point(841, 28);
            this.inversionLabel.Name = "inversionLabel";
            this.inversionLabel.Size = new System.Drawing.Size(209, 19);
            this.inversionLabel.TabIndex = 15;
            this.inversionLabel.Text = "If Inversion Report:";
            this.inversionLabel.Click += new System.EventHandler(this.label1_Click);
            // 
            // inversionReport
            // 
            this.inversionReport.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.inversionReport.Location = new System.Drawing.Point(833, 50);
            this.inversionReport.Multiline = true;
            this.inversionReport.Name = "inversionReport";
            this.inversionReport.ReadOnly = true;
            this.inversionReport.Size = new System.Drawing.Size(203, 222);
            this.inversionReport.TabIndex = 14;
            this.inversionReport.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // Report
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackgroundImage = global::teste2.Properties.Resources.shattered_island;
            this.ClientSize = new System.Drawing.Size(1352, 640);
            this.Controls.Add(this.inversionLabel);
            this.Controls.Add(this.inversionReport);
            this.Controls.Add(this.commentsLabel);
            this.Controls.Add(this.commentsReport);
            this.Controls.Add(this.commaLabel);
            this.Controls.Add(this.commaReport);
            this.Controls.Add(this.definesLabel);
            this.Controls.Add(this.definesReport);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.Export);
            this.Controls.Add(this.bracketLabel);
            this.Controls.Add(this.bracketUseReport);
            this.Controls.Add(this.spaceLabel);
            this.Controls.Add(this.spaceCountReport);
            this.Controls.Add(this.tabLabel);
            this.Controls.Add(this.tabReport);
            this.Name = "Report";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Report";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        public  System.Windows.Forms.TextBox tabReport;
        public System.Windows.Forms.Label tabLabel;
        public System.Windows.Forms.TextBox spaceCountReport;
        public System.Windows.Forms.Label spaceLabel;
        public System.Windows.Forms.TextBox bracketUseReport;
        public System.Windows.Forms.Label bracketLabel;
        private System.Windows.Forms.Button Export;
        private System.Windows.Forms.Button button2;
        public System.Windows.Forms.Label definesLabel;
        public System.Windows.Forms.TextBox definesReport;
        public System.Windows.Forms.Label commaLabel;
        public System.Windows.Forms.TextBox commaReport;
        public System.Windows.Forms.Label commentsLabel;
        public System.Windows.Forms.TextBox commentsReport;
        public System.Windows.Forms.Label inversionLabel;
        public System.Windows.Forms.TextBox inversionReport;
    }
}