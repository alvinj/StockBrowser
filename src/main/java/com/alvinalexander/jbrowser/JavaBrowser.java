//package com.alvinalexander.jbrowser;
//
//import java.awt.BorderLayout;
//import java.awt.FlowLayout;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//
//import javax.swing.BorderFactory;
//import javax.swing.JCheckBox;
//import javax.swing.JPanel;
//
//import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
//
//public class JavaBrowser extends JPanel {
//
//  public JavaBrowser() {
//    super(new BorderLayout());
//
//    // add browser to a panel, put it in the center of this panel
//    JPanel webBrowserPanel = new JPanel(new BorderLayout());
//    //webBrowserPanel.setBorder(BorderFactory.createTitledBorder("Native Web Browser component"));
//    final JWebBrowser webBrowser = new JWebBrowser();
//    webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
//    add(webBrowserPanel, BorderLayout.CENTER);
//
//    // Create an additional bar allowing to show/hide the menu bar of the web browser.
////    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
////    JCheckBox menuBarCheckBox = new JCheckBox("Menu Bar", webBrowser.isMenuBarVisible());
////    menuBarCheckBox.addItemListener(new ItemListener() {
////      public void itemStateChanged(ItemEvent e) {
////        webBrowser.setMenuBarVisible(e.getStateChange() == ItemEvent.SELECTED);
////      }
////    });
////    buttonPanel.add(menuBarCheckBox);
////    add(buttonPanel, BorderLayout.SOUTH);
//  }
//
////  /* Standard main method to try that test as a standalone application. */
////  public static void main(String[] args) {
////    UIUtils.setPreferredLookAndFeel();
////    NativeInterface.open();
////    SwingUtilities.invokeLater(new Runnable() {
////      public void run() {
////        JFrame frame = new JFrame("DJ Native Swing Test");
////        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.getContentPane().add(new JavaBrowser(), BorderLayout.CENTER);
////        frame.setSize(800, 600);
////        frame.setLocationByPlatform(true);
////        frame.setVisible(true);
////      }
////    });
////    NativeInterface.runEventPump();
////  }
//
//}
//
//
