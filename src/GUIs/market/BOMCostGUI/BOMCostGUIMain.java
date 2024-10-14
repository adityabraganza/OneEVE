package GUIs.market.BOMCostGUI;

import market.objects.Item;
import market.objects.ItemsDictionary;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;

import static market.Functions.getPrices;

public class BOMCostGUIMain extends JFrame implements ActionListener, DocumentListener{
    private String[] names;

    private final JTextArea bomInputTextArea = new JTextArea();
    private final JPanel rightPanelTextArea = new JPanel();
    private final JPanel leftPanelTextArea = new JPanel();
    private final JPanel leftPanelButtonPanel = new JPanel();
    private final JButton enterButtonTextArea = createButton("Enter", "enterTextArea", this);
    private final JButton clearButtonTextArea = createButton("Clear", "clear", this);
    private final JPanel mainLayout = new JPanel();
    private final JScrollPane scrollPaneInputTextArea = new JScrollPane(this.bomInputTextArea);
    private final JPanel menu = new JPanel();
    private final JButton textAreaButton = createButton("Paste Text", "textArea", this);
    private final JButton manualInputButton = createButton("Manual Input", "dropdown", this);
    private final JPanel leftPanelDropdown = new JPanel();
    private final JPanel inputPanelDropDown = new JPanel();
    private final JPanel inputDisplayPanelDropdown = new JPanel();
    private final JScrollPane scrollPaneDisplayDropdown = new JScrollPane(this.inputDisplayPanelDropdown);
    private JComboBox dropdownMenu = new JComboBox();
    private JTextComponent dropdownTC = new JTextField();

    public BOMCostGUIMain(){
        this.setTitle("BOM Cost");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.initializeComponents();
        this.pack();
        this.setVisible(true);
    }

    public void initializeComponents(){
        this.setLayout(new BorderLayout(1, 2));

        this.mainLayout.setLayout(new GridLayout(1, 2));

        this.leftPanelTextArea.setLayout(new GridLayout(2, 1));
        this.leftPanelButtonPanel.setLayout(new GridLayout(2,1 ));

        this.scrollPaneInputTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        this.leftPanelButtonPanel.add(this.clearButtonTextArea);
        this.leftPanelButtonPanel.add(this.enterButtonTextArea);

        this.leftPanelTextArea.add(this.scrollPaneInputTextArea);
        this.leftPanelTextArea.add(this.leftPanelButtonPanel);

        this.menu.setLayout(new GridLayout(1, 2));
        this.menu.add(this.textAreaButton);
        this.menu.add(this.manualInputButton);

        this.add(this.menu, BorderLayout.NORTH);
        this.add(mainLayout, BorderLayout.CENTER);

        this.leftPanelDropdown.setLayout(new BorderLayout());
        this.inputPanelDropDown.setLayout(new GridLayout(1, 2));

        String content = null;
        File file = new File("src/data/itemNames.txt");
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
            names = content.split("///");
        } catch (Exception e) {
            Item[] prices = getPrices();
            names = new String[prices.length];
            for (int i = 0; i < prices.length; i++){
                names[i] = prices[i].getName();
            }
        }

        this.dropdownMenu = new JComboBox(names);
        this.dropdownMenu.setEditable(true);
        this.dropdownTC = (JTextComponent) this.dropdownMenu.getEditor().getEditorComponent();
        this.dropdownTC.getDocument().addDocumentListener(this);
        this.inputPanelDropDown.add(dropdownMenu);
        this.inputPanelDropDown.add(createButton("Enter", "enterDropdownIndividual", this));
        this.leftPanelDropdown.add(this.inputPanelDropDown);

        this.scrollPaneDisplayDropdown.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.inputDisplayPanelDropdown.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height));
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateDropdown();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        System.out.println(e.getChange((Element) this.dropdownMenu));
        updateDropdown();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        System.out.println("Edited");
    }

    public JButton createButton(String buttonText, String buttonAction, ActionListener actionListener){
        JButton button = new JButton(buttonText);
        button.setActionCommand(buttonAction);
        button.addActionListener(actionListener);

        return button;
    }

    public void updateDropdown(){
        Runnable doHighlight = this::updateDropdownTemp;
        SwingUtilities.invokeLater(doHighlight);
    }

    public void updateDropdownTemp(){
        String currentText = this.dropdownMenu.getEditor().getItem().toString();
        System.out.println("Hi!");
        this.dropdownMenu.removeAllItems();

        for (String name : names) {
            if (name.contains(currentText)){
                this.dropdownMenu.addItem(name);
            }
        }
        this.dropdownMenu.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand){
            case "clear" -> bomInputTextArea.setText("");
            case "enterTextArea" -> enteredTextArea();
            case "textArea" -> textAreaGUI();
            case "dropdown" -> dropdownGUI();
            case "enterDropdownIndividual" -> enteredDropdownIndividual();
            case "dropdownChanged" -> updateDropdown();
        }

        if (actionCommand.startsWith("labelDelete")){
            String deletedItem = actionCommand.substring(11);

            for (Component component:this.inputDisplayPanelDropdown.getComponents()){
                if (Objects.equals(component.getName(), deletedItem)){
                    this.inputDisplayPanelDropdown.remove(component);
                }
            }
            this.inputDisplayPanelDropdown.repaint();
            this.pack();
        }
    }
    public void enteredDropdownIndividual(){
        Component[] components = inputDisplayPanelDropdown.getComponents();
        String newItemName = String.valueOf(this.dropdownMenu.getSelectedItem());
        boolean exists = false;
        for (Component component:components){
            if (Objects.equals(component.getName(), newItemName)){
                exists = true;
            }
        }

        boolean isValidName = false;
        for (String name:names){
            if (Objects.equals(name, newItemName)){
                isValidName = true;
            }
        }

        if (!exists && isValidName){
            JPanel temporaryPanel = new JPanel();
            temporaryPanel.setLayout(new GridLayout(1, 3));
            temporaryPanel.add(new JLabel(newItemName));
            JTextField tempTextField = new JTextField("1");
            tempTextField.setName(newItemName + "textField");
            temporaryPanel.add(tempTextField);
            JButton deleteButton = createButton("X", "labelDelete" + newItemName, this);
            temporaryPanel.add(deleteButton);
            temporaryPanel.setName(newItemName);

            this.inputDisplayPanelDropdown.setLayout(new GridLayout(Math.max(this.inputDisplayPanelDropdown.getComponentCount() + 1, 25), 0));
            this.inputDisplayPanelDropdown.add(temporaryPanel);
            this.inputDisplayPanelDropdown.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height));
            this.pack();
            this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        } else{
            for(Component component:components){
                if(component instanceof JPanel){
                    for (Component comp:((JPanel) component).getComponents()){
                        if(Objects.equals(comp.getName(), newItemName + "textField")){
                            assert comp instanceof JTextField;
                            ((JTextField) comp).setText(String.valueOf(Integer.parseInt(((JTextField) comp).getText()) + 1));
                        }
                    }
                }
            }
        }
    }
    public void dropdownGUI(){
        this.textAreaButton.setEnabled(true);
        this.manualInputButton.setEnabled(false);
        this.mainLayout.removeAll();
        this.mainLayout.add(this.leftPanelDropdown);
        this.mainLayout.add(this.scrollPaneDisplayDropdown);
        this.repaint();
        this.pack();
    }
    public void textAreaGUI(){
        this.textAreaButton.setEnabled(false);
        this.manualInputButton.setEnabled(true);
        this.mainLayout.removeAll();
        this.mainLayout.add(this.leftPanelTextArea);
        this.mainLayout.add(this.rightPanelTextArea);
        this.add(this.mainLayout, BorderLayout.CENTER);
        this.repaint();
        this.pack();
    }
    public void enteredTextArea(){
        try {
            Item[] prices = getPrices();
            assert prices != null;
            ItemsDictionary itemsDictionary = new ItemsDictionary(prices);

            String input = bomInputTextArea.getText();
            String[] inputArray = input.split("\n");
            String[][] outputArray = new String[inputArray.length][2];
            for (int i = 0; i < inputArray.length; i++){
                String[] tempArray = inputArray[i].split("\t");
                outputArray[i][0] = tempArray[0];
                outputArray[i][1] = tempArray[1];
            }
            double totalAdjusted = 0;
            double totalAverage = 0;

            for (String[] strings: outputArray){
                double priceAdjusted = itemsDictionary.getItemById(strings[0]).getAdjustedPrice() * Double.parseDouble(strings[1]);
                double priceAverage = itemsDictionary.getItemById(strings[0]).getAveragePrice() * Double.parseDouble(strings[1]);
                totalAdjusted += priceAdjusted;
                totalAverage += priceAverage;
            }
            System.out.println(totalAdjusted);
            System.out.println(totalAverage);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
