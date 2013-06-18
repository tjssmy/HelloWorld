import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.Scrollbar;

public class simple_draw extends Applet 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int xx[] = new int[1024];
    static int yy[] = new int[1024];
    int n = 0;

    public void init()
    {
        setLayout(new BorderLayout(0,0));

        toolbox tb = new toolbox();

        Panel cc = new Panel();
        Scrollbar ss = new Scrollbar(Scrollbar.HORIZONTAL);
        cc.setLayout(new BorderLayout(0,0));
        cc.add("South",new Scrollbar(Scrollbar.HORIZONTAL));
        cc.add("East",new Scrollbar(Scrollbar.VERTICAL));
        cc.add("Center",new drawarea(tb));

        Panel cm = new Panel();
        cm.setBackground(Color.black);
        cm.setLayout(new BorderLayout(1,1));
        cm.add("West",tb);
        cm.add("Center",cc);

        add("West",new blacksquare());
        add("East",new blacksquare());
        add("North",new blacksquare());
        add("South",new blacksquare());
        add("Center",cm);
    }

}

