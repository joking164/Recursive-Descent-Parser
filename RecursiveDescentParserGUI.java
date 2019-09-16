package cmsc330_project1;
/**
 * GRAMMAR
 * gui ::= Window STRING '('NUMBER','NUMBER')' layout widgets End'.'
 * layout ::= Layout layout_type':'
 * layout_type ::= Flow | Grid'('NUMBER','NUMBER [','NUMBER ',' NUMBER]')'
 * widgets ::= widget widgets | widget
 * widget ::= Button STRING ';' | Group radio_buttons End ';' | Label STRING ';' | Panel layout widgets END ';' | Textfield NUMBER ';'
 * radio_buttons ::= radio_button radio_buttons | radio_button
 * radio_button ::= Radio STRING ';'
 */

import javax.swing.*;
import java.awt.*;

public class RecursiveDescentParserGUI extends JFrame
{
    //Private variables for parser
    private String window, title, layout;
    private int count = 0, width, height;
    
    //Storage of input
    private String[] input;
    
    //Variables for GUI
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private JLabel label;
    private ButtonGroup group;
    private JRadioButton radioButton;
    
    //Constructor, starts RDP
    public RecursiveDescentParserGUI(String[] input)throws Exception
    {
        try
        {
            this.input = input;
            
            window = this.input[count];
            count++;
            checkString(window, "Window");
        
            title = findTitle();
            count++;
        
            findHeightWidth();
            count++;
            
            gui();           
        }
        catch(Exception e)
        {
            System.out.println("File could not be parsed.");
        }
    }
    
    //Checks that strings match the expected value
    private void checkString(String actual, String expected)throws Exception
    {
        if(!actual.equals(expected))
        {
            throw new CustomException(actual + " did not equal expected: " + expected);
        }
    }
    
    //Checks that title has valid input and returns it
    private String findTitle()throws Exception
    {
        char[] temp = input[count].toCharArray();
        int last = temp.length-1;
        
        checkCharacter(temp[0], '"');
        checkCharacter(temp[last], '"');
        
        char[] temp2 = new char[temp.length-2];
        
        for(int i=0; i<temp2.length; i++)
        {
            temp2[i] = temp[i+1];
        }
        
        String title = new String(temp2);
        
        return(title);
    }
    
    //Checks characters match expecte value
    private void checkCharacter(char actual, char expected)throws Exception
    {
        if(actual != expected)
        {
            throw new CustomException(actual + " did not equal expected: " + expected);
        }
    }
    
    //Finds height and width of window based of valid input
    private void findHeightWidth()throws Exception
    {
        try
        {
            char[] temp = input[count].toCharArray();
            int last = temp.length-1;
        
            checkCharacter(temp[0], '(');
            checkCharacter(temp[last], ')');
        
            int heightLength = 0, widthLength = 0;
        
            for(int i=1; i<temp.length-1; i++)
            {
                if(temp[i] == ',')
                    break;
                heightLength++;
            }
        
            for(int i=heightLength+2; i<temp.length-1; i++)
            {
                widthLength++;
            }
        
            char[] tempHeight = new char[heightLength];
            char[] tempWidth = new char[widthLength];
        
            for(int i=0; i<heightLength; i++)
            {
                tempHeight[i] = temp[i+1];
            }
        
            for(int i=0; i<widthLength; i++)
            {
                tempWidth[i] = temp[i+heightLength+2];
            }
        
            String stringHeight = new String(tempHeight);
            String stringWidth = new String(tempWidth);
        
            this.height = Integer.parseInt(stringHeight);
            this.width = Integer.parseInt(stringWidth);
        }
        catch(Exception e)
        {
            System.out.println("Error with finding height and width of window");
        }
    }
    
    //Starts contruction of GUI
    public void gui()throws Exception
    {
        frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(height, width);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        makeLayout();
        count++;
        makeWidgets();
        
        if(input[count].equals("End."));
        {
            frame.setVisible(true);
        }
    }
    
    //Starts to make layout, checks for valid input
    public void makeLayout()throws Exception
    {
        layout = input[count];
        count++;
        checkString(layout, "Layout");
        
        findLayoutType();
    }
    
    //Finds desired layout type, adds layout to frame or panel
    private void findLayoutType()throws Exception
    {
        if(input[count].equals("Flow"))
        {
            if(window.equals("Window"))
            {
                frame.setLayout(new FlowLayout());
            }
            else if(window.equals("Panel"))
            {
                panel.setLayout(new FlowLayout());
            }
            else
            {
                throw new CustomException("Error with window");
            }
        }
        else if(isGrid())
        {
            findGrid();
        }
        else
        {
            throw new CustomException(input + " did not match expected: Flow or Grid");
        }
    }
    
    //Returns true if grid input is valid
    private boolean isGrid()
    {
        char[] temp = input[count].toCharArray();
        char[] temp2 = new char[4];
        
        for(int i=0; i<temp2.length; i++)
        {
            temp2[i] = temp[i];
        }
        
        String possible = new String(temp2);
        
        return(possible.equals("Grid"));
    }
    
    //Adds grid to frame or panel
    private void findGrid()throws Exception
    {
        try
        {
            char[] temp = input[count].toCharArray();
            int last = temp.length-1;
        
            checkCharacter(temp[4], '(');
            checkCharacter(temp[last], ')');
            
            int rowLength = 0, colLength = 0, heightLength = 0, widthLength = 0;
            
            for(int i=5; i<temp.length-1; i++)
            {
                if(temp[i] == ',')
                    break;
                rowLength++;
            }
            
            for(int i=rowLength+6; i<temp.length-1; i++)
            {
                if(temp[i] == ',')
                    break;
                colLength++;
            }
            
            for(int i=rowLength+colLength+7; i<temp.length-1; i++)
            {
                if(temp[i] == ',')
                    break;
                heightLength++;
            }
            
            for(int i=rowLength+colLength+heightLength+8; i<temp.length-1; i++)
            {
                widthLength++;
            }
            
            char[] tempRow = new char[rowLength];
            char[] tempCol = new char[colLength];
            char[] tempHeight = new char[heightLength];
            char[] tempWidth = new char[widthLength];
            
            for(int i=0; i<rowLength; i++)
            {
                tempRow[i] = temp[i+5];
            }
            
            for(int i=0; i<colLength; i++)
            {
                tempCol[i] = temp[i+6+rowLength];
            }
            
            for(int i=0; i<heightLength; i++)
            {
                tempHeight[i] = temp[i+7+rowLength+colLength];
            }
            
            for(int i=0; i<widthLength; i++)
            {
                tempWidth[i] = temp[i+8+rowLength+colLength+heightLength];
            }
            
            String stringRow = new String(tempRow);
            String stringCol = new String(tempCol);
            String stringHeight = new String(tempHeight);
            String stringWidth = new String(tempWidth);
            
            int rows = Integer.parseInt(stringRow);
            int col = Integer.parseInt(stringCol);
            int ht = Integer.parseInt(stringHeight);
            int wt = Integer.parseInt(stringWidth);
            
            if(window.equals("Window"))
            {
                frame.setLayout(new GridLayout(rows, col, ht, wt));
            }
            else if(window.equals("Panel"))
            {
                panel.setLayout(new GridLayout(rows, col, ht, wt));
            }
            else
            {
                throw new CustomException("Error with window");
            }
        }
        catch(Exception e)
        {
            System.out.println(input + "Error in getting parameters for Grid layout");
        }
    }
    
    //Starts making widgets
    public void makeWidgets()throws Exception
    {
        try
        {
            if(input[count].equals("End;"))
            {
                window = "Window";
                count++;
            }
            else
            {
                findWidgetType();
            }
        }
        catch(Exception e)
        {
            System.out.println("Error with makeWidgets()");
        }
    }
    
    //Designates which widget to me made based off valid input
    public void findWidgetType()throws Exception
    {
        switch(input[count])
        {
            case "Button":
                count++;
                makeButton();
                count++;
                break;
            case "RadioButton":
                makeRadioButtons();
                break;
            case "Label":
                count++;
                makeLabel();
                count++;
                break;
            case "Panel":
                count++;
                makePanel();
                count++;
                break;
            case "Textfield":
                count++;
                makeTextField();
                count++;
                break;
            default:
                System.out.println(input[count] + " did not match any of the acceptable widgets");
        }
            
        makeWidgets();
    }
    
    //Adds button to frame or panel
    private void makeButton()throws Exception
    {
        try
        {
            char[] temp = input[count].toCharArray();
            int last = temp.length-1;
        
            checkCharacter(temp[0], '"');
            checkCharacter(temp[last], ';');
            checkCharacter(temp[last-1], '"');
        
            char[] tempButton = new char[temp.length-2];
        
            for(int i=0; i<tempButton.length-1; i++)
            {
                tempButton[i] = temp[i+1];
            }
        
            String stringButton = new String(tempButton);
        
            button = new JButton(stringButton);
        
            if(window.equals("Window"))
            {
                frame.add(button);
            }
            else if(window.equals("Panel"))
            {
                panel.add(button);
            }
            else
            {
                throw new CustomException("Error with window");
            }
        }
        catch(Exception e)
        {
            System.out.println("Error making button");
        }
    }
    
    //Makes button groups, start to make radio buttons
    public void makeRadioButtons()throws Exception
    {
        group = new ButtonGroup();
        
        radioButton();
    }
    
    //Adds radio buttons to frame or panel
    public void radioButton()
    {
        if(input[count].equals("RadioButton"))
        {
            try
            {
                count++;
                char[] temp = input[count].toCharArray();
                int last = temp.length-1;
        
                checkCharacter(temp[0], '"');
                checkCharacter(temp[last], ';');
                checkCharacter(temp[last-1], '"');
        
                char[] tempRadioButton = new char[temp.length-2];
        
                for(int i=0; i<tempRadioButton.length-1; i++)
                {
                    tempRadioButton[i] = temp[i+1];
                }
        
                String stringRadioButton = new String(tempRadioButton);
        
                radioButton = new JRadioButton(stringRadioButton);
            
                group.add(radioButton);
                
                if(window.equals("Window"))
                {
                    frame.add(radioButton);
                }
                else if(window.equals("Panel"))
                {
                    panel.add(radioButton);
                }
                else
                {
                    throw new CustomException("Error with window");
                }
                
                count++;
                radioButton();
            }
            catch(Exception e)
            {
                System.out.println("Error making button");
            }
        }
        else
        {
            
        }
    }
    
    //Adds label to frame or panel
    private void makeLabel()throws Exception
    {
        try
        {
            char[] temp = input[count].toCharArray();
            int last = temp.length-1;
        
            checkCharacter(temp[0], '"');
            checkCharacter(temp[last], ';');
            checkCharacter(temp[last-1], '"');
        
            char[] tempLabel = new char[temp.length-2];
        
            for(int i=0; i<tempLabel.length-1; i++)
            {
                tempLabel[i] = temp[i+1];
            }
        
            String stringLabel = new String(tempLabel);
        
            label = new JLabel(stringLabel);
            
            if(window.equals("Window"))
            {
                frame.add(label);
            }
            else if(window.equals("Panel"))
            {
                panel.add(label);
            }
            else
            {
                throw new CustomException("Error with window");
            }
        }
        catch(Exception e)
        {
            System.out.println("Error making label");
        }
    }
    
    //Adds panel to frame, starts layout of panel
    private void makePanel()throws Exception
    {
        window = "Panel";
        panel = new JPanel();
        makeLayout();
        frame.add(panel);
    }
    
    //Adds text field to frame or panel
    private void makeTextField()throws Exception
    {
        try
        {
            int size = Integer.parseInt(input[count]);
        
            if(window.equals("Window"))
            {
                frame.add(new JTextField(size));
            }
            else if(window.equals("Panel"))
            {
                panel.add(new JTextField(size));
            }
            else
            {
                throw new CustomException("Error with window");
            }
        }
        catch(Exception e)
        {
            System.out.println("Error making TextField");
        }
    }
}