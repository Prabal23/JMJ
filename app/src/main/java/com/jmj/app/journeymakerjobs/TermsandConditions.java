package com.jmj.app.journeymakerjobs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class TermsandConditions extends AppCompatActivity {
    Typeface fontAwesomeFont;
    WebView l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25;
    WebSettings webSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_conditions);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        back.setTypeface(fontAwesomeFont);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String line1 = "<html><body style=\"text-align:justify\"><font size=2>Please read this page carefully. It contains the terms and conditions (the 'Terms and Conditions') governing your access to and use of the JMJ.COM Web Site and the Services (as each are defined below. If you do not accept these Terms and Conditions or you do not meet or comply with their provisions, you may not use the JMJ.COM.com Web Site or Services. These Terms and Conditions are effective as of March18, 2015.</font></body></Html>";
        l1 = (WebView) findViewById(R.id.l1);
        webSettings = l1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText1 = " %s ";
        l1.loadData(String.format(htmlText1, line1), "text/html", "utf-8");

        String line2 = "<html><body style=\"text-align:justify\"><font size=2>These Terms and Conditions (as they may be amended from time to time by JMJ.COM), form a binding agreement (the 'Agreement') between you and JMJ.COM . Your access to or use of the JMJ.COM Web Sites or Services indicates your acceptance of these Terms and Conditions. You are agreeing to use the Sites at your own risk.</font></body></Html>";
        l2 = (WebView) findViewById(R.id.l2);
        webSettings = l2.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText2 = " %s ";
        l2.loadData(String.format(htmlText2, line2), "text/html", "utf-8");

        String line3 = "<html><body style=\"text-align:justify\"><font size=2>JMJ.COM means journeymakerjobs.com</font></body></Html>";
        l3 = (WebView) findViewById(R.id.l3);
        webSettings = l3.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText3 = " %s ";
        l3.loadData(String.format(htmlText3, line3), "text/html", "utf-8");

        String line4 = "<html><body style=\"text-align:justify\"><font size=2>Site' (collectively, the 'JMJ.COM Website' or the 'Sites') means Content, Text, Graphics, Design, Programming, and Services (as applicable in each context) in the journeymakerjobs.com web site.</font></body></Html>";
        l4 = (WebView) findViewById(R.id.l4);
        webSettings = l4.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText4 = " %s ";
        l4.loadData(String.format(htmlText4, line4), "text/html", "utf-8");

        String line5 = "<html><body style=\"text-align:justify\"><font size=2>'Content' includes all Text, Graphics, Design and Programming used on the Site.</font></body></Html>";
        l5 = (WebView) findViewById(R.id.l5);
        webSettings = l5.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText5 = " %s ";
        l5.loadData(String.format(htmlText5, line5), "text/html", "utf-8");

        String line6 = "<html><body style=\"text-align:justify\"><font size=2>'Employer' means a person or entity that is accessing a Site to post a job or utilizing the Services for any reason related to the purpose of seeking candidates for employment.</font></body></Html>";
        l6 = (WebView) findViewById(R.id.l6);
        webSettings = l6.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText6 = " %s ";
        l6.loadData(String.format(htmlText6, line6), "text/html", "utf-8");

        String line7 = "<html><body style=\"text-align:justify\"><font size=2>'Job Seeker' means a User who is accessing a Site to search for a job or in any other capacity except as an Employer.</font></body></Html>";
        l7 = (WebView) findViewById(R.id.l7);
        webSettings = l7.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText7 = " %s ";
        l7.loadData(String.format(htmlText7, line7), "text/html", "utf-8");

        String line8 = "<html><body style=\"text-align:justify\"><font size=2>'Services' means any services provided by JMJ.COM or its agents described herein.</font></body></Html>";
        l8 = (WebView) findViewById(R.id.l8);
        webSettings = l8.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText8 = " %s ";
        l8.loadData(String.format(htmlText8, line8), "text/html", "utf-8");

        String line9 = "<html><body style=\"text-align:justify\"><font size=2>'Text' includes all text on every page of the Sites, whether editorial, navigational, or instructional.</font></body></Html>";
        l9 = (WebView) findViewById(R.id.l9);
        webSettings = l9.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText9 = " %s ";
        l9.loadData(String.format(htmlText9, line9), "text/html", "utf-8");

        String line10 = "<html><body style=\"text-align:justify\"><font size=2>'User' refers to any individual or entity that uses any aspect of the Sites.</font></body></Html>";
        l10 = (WebView) findViewById(R.id.l10);
        webSettings = l10.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText10 = " %s ";
        l10.loadData(String.format(htmlText10, line10), "text/html", "utf-8");

        String line11 = "<html><body style=\"text-align:justify\"><font size=2>'You' or 'you' means the person who (or the entity on behalf of whom you are acting) that is agreeing to these Terms and Conditions.</font></body></Html>";
        l11 = (WebView) findViewById(R.id.l11);
        webSettings = l11.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText11 = " %s ";
        l11.loadData(String.format(htmlText11, line11), "text/html", "utf-8");

        String line12 = "<html><body style=\"text-align:justify\"><font size=2>Nothing on the Site shall be considered an endorsement, representation or warranty with respect to any User or third party, whether in regards to its web site, products, services, hiring, experience, employment or recruiting practices, or otherwise.</font></body></Html>";
        l12 = (WebView) findViewById(R.id.l12);
        webSettings = l12.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText12 = " %s ";
        l12.loadData(String.format(htmlText12, line12), "text/html", "utf-8");

        String line13 = "<html><body style=\"text-align:justify\"><font size=2>JMJ.COM assumes no responsibility for Documents posted by Users and no responsibility for the activities, omissions or other conduct of Users. JMJ.COM acts as a portal for the online distribution and publication of User submitted information and has no obligation to screen communications or information in advance and is not responsible for screening or monitoring Documents posted by Users. If notified by a User of a Document which allegedly does not conform to these Terms and Conditions, JMJ.COM may investigate the allegation and determine in good faith and in its sole discretion whether to remove or request the removal of such Document. JMJ.COM has no liability or responsibility to Users for performance or nonperformance of such activities. JMJ.COM may take any action with respect to User submitted information that it deems necessary or appropriate, in its sole discretion.</font></body></Html>";
        l13 = (WebView) findViewById(R.id.l13);
        webSettings = l13.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText13 = " %s ";
        l13.loadData(String.format(htmlText13, line13), "text/html", "utf-8");

        String line14 = "<html><body style=\"text-align:justify\"><font size=2>JMJ.COM reserves the right to review and delete (or modify) any content that we believe, in our sole discretion, violates our Terms or other applicable policies or inappropriate.</font></body></Html>";
        l14 = (WebView) findViewById(R.id.l14);
        webSettings = l14.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText14 = " %s ";
        l14.loadData(String.format(htmlText14, line14), "text/html", "utf-8");

        String line15 = "<html><body style=\"text-align:justify\"><font size=2>Users are entirely responsible for maintaining the confidentiality of their password. Users are solely responsible for any and all use of their account. Passwords are subject to cancellation or suspension by JMJ.COM at any time.</font></body></Html>";
        l15 = (WebView) findViewById(R.id.l15);
        webSettings = l15.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText15 = " %s ";
        l15.loadData(String.format(htmlText15, line15), "text/html", "utf-8");

        String line16 = "<html><body style=\"text-align:justify\"><font size=2>You will not use any information obtained from JMJ.COM in order to harass, abuse, or harm another person, or in order to contact, advertise to, solicit, or sell to any visitor without their prior explicit consent.</font></body></Html>";
        l16 = (WebView) findViewById(R.id.l16);
        webSettings = l16.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText16 = " %s ";
        l16.loadData(String.format(htmlText16, line16), "text/html", "utf-8");

        String line17 = "<html><body style=\"text-align:justify\"><font size=2>JMJ.COM may contain links to third-party websites placed by us as a service to those interested in this information, or posted by other Members. Your use of all such links to third-party websites is at your own risk. We do not monitor or have any control over, and make no claim or representation regarding third-party websites.</font></body></Html>";
        l17 = (WebView) findViewById(R.id.l17);
        webSettings = l17.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText17 = " %s ";
        l17.loadData(String.format(htmlText17, line17), "text/html", "utf-8");

        String line18 = "<html><body style=\"text-align:justify\"><font size=2>Any information you provide about your current, past or potential status as an employee of a certain employer is correct and complete.</font></body></Html>";
        l18 = (WebView) findViewById(R.id.l18);
        webSettings = l18.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText18 = " %s ";
        l18.loadData(String.format(htmlText18, line18), "text/html", "utf-8");

        String line19 = "<html><body style=\"text-align:justify\"><font size=2>Any CV you upload is accurate and submitted on your own behalf.</font></body></Html>";
        l19 = (WebView) findViewById(R.id.l19);
        webSettings = l19.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText19 = " %s ";
        l19.loadData(String.format(htmlText19, line19), "text/html", "utf-8");

        String line20 = "<html><body style=\"text-align:justify\"><font size=2><b>You agree that you will not post any Content that:</b></font></body></Html>";
        l20 = (WebView) findViewById(R.id.l20);
        webSettings = l20.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText20 = " %s ";
        l20.loadData(String.format(htmlText20, line20), "text/html", "utf-8");

        String line21 = "<html><body style=\"text-align:justify\"><font size=2>Is offensive or promotes racism, bigotry, hatred or physical harm of any kind against any group or individual, bullies, harasses or advocates stalking, bullying, or harassment of another person.</font></body></Html>";
        l21 = (WebView) findViewById(R.id.l21);
        webSettings = l21.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText21 = " %s ";
        l21.loadData(String.format(htmlText21, line21), "text/html", "utf-8");

        String line22 = "<html><body style=\"text-align:justify\"><font size=2>Involves the transmission of 'junk mail', 'chain letters', or unsolicited mass mailing, or 'spamming'.</font></body></Html>";
        l22 = (WebView) findViewById(R.id.l22);
        webSettings = l22.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText22 = " %s ";
        l22.loadData(String.format(htmlText22, line22), "text/html", "utf-8");

        String line23 = "<html><body style=\"text-align:justify\"><font size=2>Is false or misleading or promotes, endorses or furthers illegal activities or conduct that is abusive, threatening, obscene, defamatory or libelous.</font></body></Html>";
        l23 = (WebView) findViewById(R.id.l23);
        webSettings = l23.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText23 = " %s ";
        l23.loadData(String.format(htmlText23, line23), "text/html", "utf-8");

        String line24 = "<html><body style=\"text-align:justify\"><font size=2>Contains viruses, Trojan horses, worms, corrupted files, or similar software.</font></body></Html>";
        l24 = (WebView) findViewById(R.id.l24);
        webSettings = l24.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText24 = " %s ";
        l24.loadData(String.format(htmlText24, line24), "text/html", "utf-8");

        String line25 = "<html><body style=\"text-align:justify\"><font size=2>Contains any logo or brands, or link to website, other than your own or those of any entity for which you are authorized to submit job ads; reasonable discretion.</font></body></Html>";
        l25 = (WebView) findViewById(R.id.l25);
        webSettings = l25.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlText25 = " %s ";
        l25.loadData(String.format(htmlText25, line25), "text/html", "utf-8");
    }
}
