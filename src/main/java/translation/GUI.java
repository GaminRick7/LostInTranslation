package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {
    public static void main(String[] args) {
        Translator translator = new JSONTranslator();
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

        SwingUtilities.invokeLater(() -> {
            JPanel languagePanel = new JPanel();
            final JComboBox<String> languageComboBox = new JComboBox();

            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(languageCodeConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(new JLabel("Language: "));
            languagePanel.add(languageComboBox);

            JPanel countryPanel = new JPanel();
            String[] items = new String[translator.getCountryCodes().size()];
            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] = countryCodeConverter.fromCountryCode(countryCode);
            }

            final JList<String> list = new JList(items);
            list.setSelectionMode(1);
            JScrollPane scrollPane = new JScrollPane(list);
            countryPanel.add(scrollPane);




            JPanel buttonPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);
            languageComboBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == 1) {
                        String language = languageCodeConverter.fromLanguage(languageComboBox.getSelectedItem().toString());
                        String country = countryCodeConverter.fromCountry(list.getSelectedValue());
                        System.out.println(language + "\t" + country);
                        String result = translator.translate(country, language);
                        if (result == null) {
                            result = "no translation found!";
                        }
                        resultLabel.setText(result);

                    }

                }
            });

            list.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    String language = languageCodeConverter.fromLanguage(languageComboBox.getSelectedItem().toString());
                    String country = countryCodeConverter.fromCountry(list.getSelectedValue());
                    System.out.println(language + "\t" + country);
                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }
            });


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);



            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
