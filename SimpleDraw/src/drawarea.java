import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Rectangle;

public class drawarea extends Panel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int n;
    toolbox tb;
    grobj_list glist = new grobj_list();
    grobj_list clist = new grobj_list();
    int old_x,old_y;
    int mode;
    int bx0,by0,bx1,by1;
    boolean bvis = false;
    int grid = 8;

    drawarea(toolbox t)
    {
        tb = t;
        setBackground(Color.white);
        t.parent = this;
    }

    public void paint(Graphics g) 
    {
        Rectangle r = bounds();

        if(grid>0)
        {
            g.setColor(new Color(224,224,224));

            for(int i=grid;i<r.height;i+=grid)
                g.drawLine(0,i,r.width,i);
            for(int i=grid;i<r.width;i+=grid)
                g.drawLine(i,0,i,r.height);

            g.setColor(new Color(208,208,208));

            for(int i=grid*10;i<r.height;i+=grid*10)
                g.drawLine(0,i,r.width,i);
            for(int i=grid*10;i<r.width;i+=grid*10)
                g.drawLine(i,0,i,r.height);
        }

        for(grobj i=glist.ghead;i!=null;i=i.next)
            i.paint(g);

        if(bvis)
        {
            g.setColor(Color.red);
            g.drawLine(bx0,by0,bx1,by0);
            g.drawLine(bx1,by0,bx1,by1);
            g.drawLine(bx1,by1,bx0,by1);
            g.drawLine(bx0,by1,bx0,by0);
        }
    }

    public boolean keyDown(Event e,int k)
    {
        if(k=='x' || k=='X')
        {
            grobj i,ii;
            clist.ghead = null;
            clist.gtail = null;

            for(i = glist.ghead;i!=null;i=i.next)
                if(i.select!=0)
                    i.copy(clist);
            for(i = glist.ghead;i!=null;i=ii)
            {
                ii = i.next;
                if(i.select!=0)
                    i.delete();
            }
            repaint();
        }
        else if(k=='c' || k=='C')
        {
            grobj i;
            clist.ghead = null;
            clist.gtail = null;

            for(i = glist.ghead;i!=null;i=i.next)
                if(i.select!=0)
                    i.copy(clist);
        }
        else if(k=='v' || k=='V')
        {
            grobj i;

            for(i = glist.ghead;i!=null;i=i.next)
                i.select = 0;
            for(i = clist.ghead;i!=null;i=i.next)
            {
                (i.copy(glist)).select = 1;
            }
            repaint();
        }
        else if(k=='o' || k=='O')
        {
            if(grid==0) grid = 8;
            else if(grid==8) grid = 4;
            else grid = 0;
            repaint();
        }
        else if(k=='g' || k=='G')
        {
            new grobj_group(glist,glist);
            repaint();
        }
        else if(k=='u' || k=='U')
        {
            grobj i,ii;
            grobj gts = glist.gtail;

            for(i = glist.ghead;i!=null;i=ii)
            {
                ii = i.next;
                if(i.select!=0)
                    i.ungroup();
                if(i==gts)
                    break;
            }
            repaint();
        }
        return(true);
    }

    public void newstatus(int s)
    {
        for(grobj j = glist.ghead;j!=null;j=j.next)
            j.select = 0;
        repaint();
    }

    public void newcolor(Color c)
    {
        for(grobj j = glist.ghead;j!=null;j=j.next)
            if(j.select!=0)
                j.setcolor(c);
        repaint();
    }

    public boolean mouseDown(Event e,int x,int y)
    {
        int xs = x;
        int ys = y;
        if(grid>0)
        {
            x = ((x+(grid/2))/grid)*grid;
            y = ((y+(grid/2))/grid)*grid;
        }
        mode = 0;
        if(tb.status==0)
        {
            grobj i;
            for(i = glist.ghead;i!=null;i=i.next)
                if(i.select!=0)
                    i.select = 1;
            for(i = glist.gtail;i!=null;i=i.prev)
                if(i.select==1)
                    if(i.selectpoint(x,y))
                        break;
            if(i==null)
            {
                for(i = glist.gtail;i!=null;i=i.prev)
                    if(i.select==1)
                    {
                        if(i.selectobj(x,y))
                            break;
                    }
                    else
                    {
                        if(i.selectobj(x,y))
                        {
                            if(!e.shiftDown())
                                for(grobj j = glist.ghead;j!=null;j=j.next)
                                    if(j!=i)
                                        j.select = 0;
                            break;
                        }
                    }
                if(i==null)
                {
                    if(!e.shiftDown())
                        for(grobj j = glist.ghead;j!=null;j=j.next)
                            j.select = 0;
                    bvis = true;
                    bx0 = bx1 = xs;
                    by0 = by1 = ys;
                }
                mode = 1;
            }
        }
        else
        {
            grobj g;
            grobj i;
            for(i = glist.ghead;i!=null;i=i.next)
                if(i.select!=0)
                    break;

            if(i!=null && i.select!=0)
                i.addpoint(x,y);
            else if(tb.status==1)
                g = new grobj_line(x,y,tb.cstat,glist);
            else if(tb.status==2)
                g = new grobj_rect(x,y,tb.cstat,glist);
            else if(tb.status==3)
                g = new grobj_oval(x,y,tb.cstat,glist);
            else if(tb.status==4)
                g = new grobj_poly(x,y,tb.cstat,glist);
        }
        repaint();
        old_x = x;
        old_y = y;
        return(true);
    }

    public boolean mouseDrag(Event e,int x,int y)
    {
        if(bvis)
        {
            int xmin = (bx1<bx0) ? bx1 : bx0;
            int ymin = (by1<by0) ? by1 : by0;
            int xmax = (bx1>bx0) ? bx1 : bx0;
            int ymax = (by1>by0) ? by1 : by0;
            xmin = (xmin<x) ? xmin : x;
            ymin = (ymin<y) ? ymin : y;
            xmax = (xmax>x) ? xmax : x;
            ymax = (ymax>y) ? ymax : y;
            bx1 = x;
            by1 = y;
            repaint(xmin-2,ymin-2,xmax-xmin+4,ymax-ymin+4);
        }
        else 
        {
            if(grid>0)
            {
                x = ((x+(grid/2))/grid)*grid;
                y = ((y+(grid/2))/grid)*grid;
            }
            int xmin = 10000;
            int ymin = 10000;
            int xmax = -10000;
            int ymax = -10000;
            for(grobj i = glist.ghead;i!=null;i=i.next)
                if(i.select>1 && mode==0 || i.select==1 && mode==1)
                {
                    if(i.xmin<xmin) xmin = i.xmin;
                    if(i.ymin<ymin) ymin = i.ymin;
                    if(i.xmax>xmax) xmax = i.xmax;
                    if(i.ymax>ymax) ymax = i.ymax;
                    if(mode==0)
                        i.movepoint(x-old_x,y-old_y);
                    else 
                        i.moveobj(x-old_x,y-old_y);
                    if(i.xmin<xmin) xmin = i.xmin;
                    if(i.ymin<ymin) ymin = i.ymin;
                    if(i.xmax>xmax) xmax = i.xmax;
                    if(i.ymax>ymax) ymax = i.ymax;
                }
            if(xmin<xmax)
                repaint(xmin-2,ymin-2,xmax-xmin+4,ymax-ymin+4);
        }
        old_x = x;
        old_y = y;
        return(true);
    }

    public boolean mouseUp(Event e,int x,int y)
    {
        if(bvis)
        {
            bx1 = x;
            by1 = y;
            int xmin = (bx1<bx0) ? bx1 : bx0;
            int ymin = (by1<by0) ? by1 : by0;
            int xmax = (bx1>bx0) ? bx1 : bx0;
            int ymax = (by1>by0) ? by1 : by0;
            for(grobj j = glist.ghead;j!=null;j=j.next)
                j.selectrect(xmin,ymin,xmax,ymax);
            bvis = false;
            repaint();
        }
        else
        {
            if(grid>0)
            {
                x = ((x+(grid/2))/grid)*grid;
                y = ((y+(grid/2))/grid)*grid;
            }
            int xmin = 10000;
            int ymin = 10000;
            int xmax = -10000;
            int ymax = -10000;
            for(grobj i = glist.ghead;i!=null;i=i.next)
                if(i.select>1 && mode==0 || i.select==1 && mode==1)
                {
                    if(i.xmin<xmin) xmin = i.xmin;
                    if(i.ymin<ymin) ymin = i.ymin;
                    if(i.xmax>xmax) xmax = i.xmax;
                    if(i.ymax>ymax) ymax = i.ymax;
                    if(mode==0)
                        i.movepoint(x-old_x,y-old_y);
                    else 
                        i.moveobj(x-old_x,y-old_y);
                    if(i.xmin<xmin) xmin = i.xmin;
                    if(i.ymin<ymin) ymin = i.ymin;
                    if(i.xmax>xmax) xmax = i.xmax;
                    if(i.ymax>ymax) ymax = i.ymax;
                    if(tb.status!=0)
                        if(i.done())
                            i.select = 0;
                }
            if(xmin<xmax)
                repaint(xmin-2,ymin-2,xmax-xmin+4,ymax-ymin+4);
        }
        old_x = x;
        old_y = y;
        return(true);
    }
}

