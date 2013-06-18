import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Polygon;
import java.awt.Rectangle;

public class toolbox extends Panel
{
    int n;
    int status = 0;
    Color cstat = new Color(254,254,254);
    drawarea parent;

    toolbox()
    {
        setBackground(Color.white);
    }

    public void paint(Graphics g) 
    {
        Rectangle r = bounds();


        for(int i=0;i<5;i++)
        {
            if(i==status)
            {
                g.setColor(new Color(192,192,192));
                g.fillRect(0,i*33,32,32);
            }
            g.setColor(Color.black);
            g.drawLine(0,i*33+32,32,i*33+32);
            if(i==0)
            {
                Polygon p = new Polygon();
                p.addPoint(24,i*33+8);
                p.addPoint(16,i*33+8);
                p.addPoint(24,i*33+16);
                g.fillPolygon(p);
                g.drawLine(8,i*33+24,20,i*33+12);
            }
            else if(i==1)
            {
                g.drawLine(8,i*33+24,24,i*33+8);
            }
            else if(i==2)
            {
                g.setColor(cstat);
                g.fillRect(8,i*33+8,16,16);
                g.setColor(Color.black);
                g.drawRect(8,i*33+8,16,16);
            }
            else if(i==3)
            {
                g.setColor(cstat);
                g.fillOval(8,i*33+8,16,16);
                g.setColor(Color.black);
                g.drawOval(8,i*33+8,16,16);
            }
            else if(i==4)
            {
                Polygon p = new Polygon();
                p.addPoint(8,i*33+8);
                p.addPoint(16,i*33+24);
                p.addPoint(24,i*33+8);
                p.addPoint(16,i*33+12);
                p.addPoint(8,i*33+8);
                g.setColor(cstat);
                g.fillPolygon(p);
                g.setColor(Color.black);
                g.drawPolygon(p);
            }
        }
        for(int j = 0;j<9;j++)
            for(int i = 0;i<3;i++)
            {
                g.setColor(new Color((j%3)*127,(j/3)*127,i*127));
                g.fillRect(2+i*9,180+j*9,9,9);
            }
        g.setColor(Color.black);
        for(int i = 0;i<=9;i++)
            g.drawLine(2,180+i*9,29,180+i*9);
        for(int i = 0;i<=3;i++)
            g.drawLine(2+i*9,180,2+i*9,180+81);
    }

    public Dimension preferredSize()
    {
        return(new Dimension(32,32));
    }

    public boolean mouseDown(Event e,int x,int y)
    {
        if(y<5*33)
        {
            int oldstatus = status;
            status = y/33;
            if(status<0) status = 0;
            if(status>4) status = 3;
            if(oldstatus!=status)
            {
                parent.newstatus(status);
                repaint();
            }
        }
        else if(y>180 && y<180+81)
        {
            int xx = ((x-2)/9);
            int yy = ((y-180)/9);
            if(xx<0) xx = 0;
            if(xx>2) xx = 2;
            if(yy<0) yy = 0;
            if(yy>8) yy = 8;
            parent.newcolor(new Color((yy%3)*127,(yy/3)*127,xx*127));
            if(status!=0)
            {
                cstat = new Color((yy%3)*127,(yy/3)*127,xx*127);
                repaint();
            }
        }
        return(true);
    }
}

