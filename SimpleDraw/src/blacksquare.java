import java.awt.*;
import java.applet.*;

public class blacksquare extends Panel
{
    int n;

    blacksquare()
    {
    }

    public void paint(Graphics g) 
    {
        Rectangle r;

        r = bounds();
        g.setColor(Color.black);
        g.fillRect(0,0,r.width,r.height);
    }

    public Dimension preferredSize()
    {
        return(new Dimension(1,1));
    }
}

