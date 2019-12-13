namespace teste2
{
    partial class Form1
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.richTextBox1 = new System.Windows.Forms.RichTextBox();
            this.checkForTabs = new System.Windows.Forms.CheckBox();
            this.checkForSpaceCount = new System.Windows.Forms.CheckBox();
            this.checkForBracketUse = new System.Windows.Forms.CheckBox();
            this.button1 = new System.Windows.Forms.Button();
            this.checkForDefines = new System.Windows.Forms.CheckBox();
            this.checkForComma = new System.Windows.Forms.CheckBox();
            this.checkForUnused = new System.Windows.Forms.CheckBox();
            this.checkForComments = new System.Windows.Forms.CheckBox();
            this.SuspendLayout();
            // 
            // richTextBox1
            // 
            this.richTextBox1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.richTextBox1.Location = new System.Drawing.Point(12, 12);
            this.richTextBox1.Name = "richTextBox1";
            this.richTextBox1.Size = new System.Drawing.Size(708, 624);
            this.richTextBox1.TabIndex = 2;
            this.richTextBox1.Text = "";
            // 
            // checkForTabs
            // 
            this.checkForTabs.AutoSize = true;
            this.checkForTabs.BackColor = System.Drawing.Color.Transparent;
            this.checkForTabs.Font = new System.Drawing.Font("Monospac821 BT", 7.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.checkForTabs.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.checkForTabs.Location = new System.Drawing.Point(842, 27);
            this.checkForTabs.Name = "checkForTabs";
            this.checkForTabs.Size = new System.Drawing.Size(124, 18);
            this.checkForTabs.TabIndex = 3;
            this.checkForTabs.Text = "Check for Tabs";
            this.checkForTabs.UseVisualStyleBackColor = false;
            // 
            // checkForSpaceCount
            // 
            this.checkForSpaceCount.AutoSize = true;
            this.checkForSpaceCount.BackColor = System.Drawing.Color.Transparent;
            this.checkForSpaceCount.Font = new System.Drawing.Font("Monospac821 BT", 7.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.checkForSpaceCount.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.checkForSpaceCount.Location = new System.Drawing.Point(842, 51);
            this.checkForSpaceCount.Name = "checkForSpaceCount";
            this.checkForSpaceCount.Size = new System.Drawing.Size(173, 18);
            this.checkForSpaceCount.TabIndex = 4;
            this.checkForSpaceCount.Text = "Check for Space Count";
            this.checkForSpaceCount.UseVisualStyleBackColor = false;
            // 
            // checkForBracketUse
            // 
            this.checkForBracketUse.AutoSize = true;
            this.checkForBracketUse.BackColor = System.Drawing.Color.Transparent;
            this.checkForBracketUse.Font = new System.Drawing.Font("Monospac821 BT", 7.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.checkForBracketUse.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.checkForBracketUse.Location = new System.Drawing.Point(842, 74);
            this.checkForBracketUse.Name = "checkForBracketUse";
            this.checkForBracketUse.Size = new System.Drawing.Size(173, 18);
            this.checkForBracketUse.TabIndex = 5;
            this.checkForBracketUse.Text = "Check for Bracket Use";
            this.checkForBracketUse.UseVisualStyleBackColor = false;
            // 
            // button1
            // 
            this.button1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(128)))), ((int)(((byte)(0)))));
            this.button1.FlatAppearance.BorderColor = System.Drawing.Color.Black;
            this.button1.FlatAppearance.BorderSize = 2;
            this.button1.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.button1.Location = new System.Drawing.Point(948, 613);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 6;
            this.button1.Text = "Scan";
            this.button1.UseVisualStyleBackColor = false;
            this.button1.Click += new System.EventHandler(this.Button1_Click);
            // 
            // checkForDefines
            // 
            this.checkForDefines.AutoSize = true;
            this.checkForDefines.BackColor = System.Drawing.Color.Transparent;
            this.checkForDefines.Font = new System.Drawing.Font("Monospac821 BT", 7.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.checkForDefines.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.checkForDefines.Location = new System.Drawing.Point(842, 98);
            this.checkForDefines.Name = "checkForDefines";
            this.checkForDefines.Size = new System.Drawing.Size(117, 18);
            this.checkForDefines.TabIndex = 7;
            this.checkForDefines.Text = "Check Defines";
            this.checkForDefines.UseVisualStyleBackColor = false;
            // 
            // checkForComma
            // 
            this.checkForComma.AutoSize = true;
            this.checkForComma.BackColor = System.Drawing.Color.Transparent;
            this.checkForComma.Font = new System.Drawing.Font("Monospac821 BT", 7.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.checkForComma.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.checkForComma.Location = new System.Drawing.Point(842, 121);
            this.checkForComma.Name = "checkForComma";
            this.checkForComma.Size = new System.Drawing.Size(145, 18);
            this.checkForComma.TabIndex = 8;
            this.checkForComma.Text = "Check Comma-Space";
            this.checkForComma.UseVisualStyleBackColor = false;
            // 
            // checkForUnused
            // 
            this.checkForUnused.AutoSize = true;
            this.checkForUnused.BackColor = System.Drawing.Color.Transparent;
            this.checkForUnused.Font = new System.Drawing.Font("Monospac821 BT", 7.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.checkForUnused.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.checkForUnused.Location = new System.Drawing.Point(842, 145);
            this.checkForUnused.Name = "checkForUnused";
            this.checkForUnused.Size = new System.Drawing.Size(180, 18);
            this.checkForUnused.TabIndex = 9;
            this.checkForUnused.Text = "Check Unused Variables";
            this.checkForUnused.UseVisualStyleBackColor = false;
            // 
            // checkForComments
            // 
            this.checkForComments.AutoSize = true;
            this.checkForComments.BackColor = System.Drawing.Color.Transparent;
            this.checkForComments.Font = new System.Drawing.Font("Monospac821 BT", 7.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.checkForComments.ForeColor = System.Drawing.Color.MediumTurquoise;
            this.checkForComments.Location = new System.Drawing.Point(842, 168);
            this.checkForComments.Name = "checkForComments";
            this.checkForComments.Size = new System.Drawing.Size(124, 18);
            this.checkForComments.TabIndex = 10;
            this.checkForComments.Text = "Check Comments";
            this.checkForComments.UseVisualStyleBackColor = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackgroundImage = global::teste2.Properties.Resources.shattered_island;
            this.ClientSize = new System.Drawing.Size(1107, 680);
            this.Controls.Add(this.checkForComments);
            this.Controls.Add(this.checkForUnused);
            this.Controls.Add(this.checkForComma);
            this.Controls.Add(this.checkForDefines);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.checkForBracketUse);
            this.Controls.Add(this.checkForSpaceCount);
            this.Controls.Add(this.checkForTabs);
            this.Controls.Add(this.richTextBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.RichTextBox richTextBox1;
        private System.Windows.Forms.CheckBox checkForTabs;
        private System.Windows.Forms.CheckBox checkForSpaceCount;
        private System.Windows.Forms.CheckBox checkForBracketUse;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.CheckBox checkForDefines;
        private System.Windows.Forms.CheckBox checkForComma;
        private System.Windows.Forms.CheckBox checkForUnused;
        private System.Windows.Forms.CheckBox checkForComments;
    }
}

