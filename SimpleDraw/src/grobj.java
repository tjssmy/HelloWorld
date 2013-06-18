import java.awt.*;
import java.applet.*;

class grobj_list extends Object
{
    grobj ghead,gtail;

    grobj_list()
    {
        ghead = null;
        gtail = null;
    }
}

public class grobj extends Object
{
    grobj next,prev;
    int select;
    int xmin,ymin,xmax,ymax;
    grobj_list parent;

    grobj(grobj_list p)
    {
        link(p);
    }

    public void link(grobj_list p)
    {
        next = null;
        prev = p.gtail;
        parent = p;
        if(p.ghead==null)
            p.ghead = this;
        else
            p.gtail.next = this;
        p.gtail = this;
    }

    public void ungroup()
    {
    }

    public boolean done()
    {
        return(true);
    }

    public void addpoint(int x,int y)
    {
    }

    public void unlink()
    {
        if(next==null)
            parent.gtail = prev;
        else
            next.prev = prev;

        if(prev==null)
            parent.ghead = next;
        else
            prev.next = next;
        next = null;
        prev = null;
    }

    public grobj copy(grobj_list gl)
    {
        return(null);
    }

    public void setcolor(Color c)
    {
    }

    public void delete()
    {
        unlink();
    }

    public void paint(Graphics g) 
    {
    }

    public void moveobj(int x,int y)
    {
    }

    public void movepoint(int x,int y)
    {
    }

    public boolean selectpoint(int x,int y)
    {
        return(false);
    }

    public boolean selectobj(int x,int y)
    {
        return(false);
    }

    public boolean selectrect(int x0,int y0,int x1,int y1)
    {
        return(false);
    }
}

class grobj_rect extends grobj
{
    Color c;
    int x0,y0,x1,y1;

    grobj_rect(int xx0,int yy0,Color cc,grobj_list p)
    {
        super(p);
        x0 = x1 = xmin = xmax = xx0;
        y0 = y1 = ymin = ymax = yy0;
        c = cc;
        select = 5;
    }

    public grobj copy(grobj_list gl)
    {
        grobj_rect g;

        g = new grobj_rect(x0,y0,c,gl);
        g.movepoint(x1-x0,y1-y0);
        g.select = 0;
        return(g);
    }

    public void setcolor(Color cc)
    {
        c = cc;
    }

    public void paint(Graphics g) 
    {
        int x = x0;
        int y = y0;
        int w = x1-x0;
        int h = y1-y0;
        if(x>x1) x = x1;
        if(y>y1) y = y1;
        if(w<0) w = -w;
        if(h<0) h = -h;

        g.setColor(c);
        g.fillRect(x,y,w,h);
        g.setColor(Color.black);
        g.drawRect(x,y,w,h);
        if(select!=0)
        {
            g.setColor(Color.red);
            g.drawRect(x,y,w,h);
            g.fillRect(x-1,y-1,3,3);
            g.fillRect(x+w-1,y-1,3,3);
            g.fillRect(x-1,y+h-1,3,3);
            g.fillRect(x+w-1,y+h-1,3,3);
        }
        g.setColor(Color.black);
    }

    public void moveobj(int x,int y)
    {
        x1 += x;
        x0 += x;
        y1 += y;
        y0 += y;
        if(x0<x1) { xmin = x0 ; xmax = x1 ; }
        else      { xmin = x1 ; xmax = x0 ; }
        if(y0<y1) { ymin = y0 ; ymax = y1 ; }
        else      { ymin = y1 ; ymax = y0 ; }
    }

    public void movepoint(int x,int y)
    {
        if(select==5 || select==3)
            x1 += x;
        if(select==4 || select==2)
            x0 += x;
        if(select==5 || select==4)
            y1 += y;
        if(select==3 || select==2)
            y0 += y;
        if(x0<x1) { xmin = x0 ; xmax = x1 ; }
        else      { xmin = x1 ; xmax = x0 ; }
        if(y0<y1) { ymin = y0 ; ymax = y1 ; }
        else      { ymin = y1 ; ymax = y0 ; }
    }

    public boolean selectpoint(int x,int y)
    {
        if(x-x0<3 && x-x0>-3 && y-y0<3 && y-y0>-3)
        {
            select = 2;
            return(true);
        }
        if(x-x1<3 && x-x1>-3 && y-y0<3 && y-y0>-3)
        {
            select = 3;
            return(true);
        }
        if(x-x0<3 && x-x0>-3 && y-y1<3 && y-y1>-3)
        {
            select = 4;
            return(true);
        }
        else if(x-x1<3 && x-x1>-3 && y-y1<3 && y-y1>-3)
        {
            select = 5;
            return(true);
        }
        return(false);
    }

    public boolean selectobj(int x,int y)
    {
        if(x>=xmin && x<=xmax && y>=ymin && y<=ymax)
        {
            select = 1;
            return(true);
        }
        return(false);
    }

    public boolean selectrect(int xx0,int yy0,int xx1,int yy1)
    {
        if(xx0<x0 && xx1>x1 && yy0<y0 && yy1>y1)
        {
            select = 1;
            return(true);
        }
        return(false);
    }
}

class grobj_oval extends grobj
{
    Color c;
    int x0,y0,x1,y1;

    grobj_oval(int xx0,int yy0,Color cc,grobj_list p)
    {
        super(p);
        x0 = x1 = xmin = xmax = xx0;
        y0 = y1 = ymin = ymax = yy0;
        c = cc;
        select = 5;
    }

    public grobj copy(grobj_list gl)
    {
        grobj_oval g;

        g = new grobj_oval(x0,y0,c,gl);
        g.movepoint(x1-x0,y1-y0);
        g.select = 0;
        return(g);
    }

    public void setcolor(Color cc)
    {
        c = cc;
    }

    public void paint(Graphics g) 
    {
        int x = x0;
        int y = y0;
        int w = x1-x0;
        int h = y1-y0;
        if(x>x1) x = x1;
        if(y>y1) y = y1;
        if(w<0) w = -w;
        if(h<0) h = -h;

        g.setColor(c);
        g.fillOval(x,y,w,h);
        g.setColor(Color.black);
        g.drawOval(x,y,w,h);
        if(select!=0)
        {
            g.setColor(Color.red);
            g.drawRect(x,y,w,h);
            g.fillRect(x-1,y-1,3,3);
            g.fillRect(x+w-1,y-1,3,3);
            g.fillRect(x-1,y+h-1,3,3);
            g.fillRect(x+w-1,y+h-1,3,3);
        }
        g.setColor(Color.black);
    }

    public void moveobj(int x,int y)
    {
        x1 += x;
        x0 += x;
        y1 += y;
        y0 += y;
        if(x0<x1) { xmin = x0 ; xmax = x1 ; }
        else      { xmin = x1 ; xmax = x0 ; }
        if(y0<y1) { ymin = y0 ; ymax = y1 ; }
        else      { ymin = y1 ; ymax = y0 ; }
    }

    public void movepoint(int x,int y)
    {
        if(select==5 || select==3)
            x1 += x;
        if(select==4 || select==2)
            x0 += x;
        if(select==5 || select==4)
            y1 += y;
        if(select==3 || select==2)
            y0 += y;
        if(x0<x1) { xmin = x0 ; xmax = x1 ; }
        else      { xmin = x1 ; xmax = x0 ; }
        if(y0<y1) { ymin = y0 ; ymax = y1 ; }
        else      { ymin = y1 ; ymax = y0 ; }
    }

    public boolean selectpoint(int x,int y)
    {
        if(x-x0<3 && x-x0>-3 && y-y0<3 && y-y0>-3)
        {
            select = 2;
            return(true);
        }
        if(x-x1<3 && x-x1>-3 && y-y0<3 && y-y0>-3)
        {
            select = 3;
            return(true);
        }
        if(x-x0<3 && x-x0>-3 && y-y1<3 && y-y1>-3)
        {
            select = 4;
            return(true);
        }
        else if(x-x1<3 && x-x1>-3 && y-y1<3 && y-y1>-3)
        {
            select = 5;
            return(true);
        }
        return(false);
    }

    public boolean selectobj(int x,int y)
    {
        if(x>=xmin && x<=xmax && y>=ymin && y<=ymax)
        {
            select = 1;
            return(true);
        }
        return(false);
    }

    public boolean selectrect(int xx0,int yy0,int xx1,int yy1)
    {
        if(xx0<x0 && xx1>x1 && yy0<y0 && yy1>y1)
        {
            select = 1;
            return(true);
        }
        return(false);
    }
}

class grobj_line extends grobj
{
    int x0,y0,x1,y1;

    grobj_line(int xx0,int yy0,Color cc,grobj_list p)
    {
        super(p);
        x0 = x1 = xmin = xmax = xx0;
        y0 = y1 = ymin = ymax = yy0;
        select = 3;
    }

    public grobj copy(grobj_list gl)
    {
        grobj_line g;

        g = new grobj_line(x0,y0,Color.black,gl);
        g.movepoint(x1-x0,y1-y0);
        g.select = 0;
        return(g);
    }

    public void paint(Graphics g) 
    {
        if(select!=0)
        {
            g.setColor(Color.red);
            g.drawLine(x0,y0,x1,y1);
            g.fillRect(x0-1,y0-1,3,3);
            g.fillRect(x1-1,y1-1,3,3);
        }
        else
        {
            g.setColor(Color.black);
            g.drawLine(x0,y0,x1,y1);
        }
        g.setColor(Color.black);
    }

    public void moveobj(int x,int y)
    {
        x1 += x;
        x0 += x;
        y1 += y;
        y0 += y;
        if(x0<x1) { xmin = x0 ; xmax = x1 ; }
        else      { xmin = x1 ; xmax = x0 ; }
        if(y0<y1) { ymin = y0 ; ymax = y1 ; }
        else      { ymin = y1 ; ymax = y0 ; }
    }

    public void movepoint(int x,int y)
    {
        if(select==2)
        {
            x0 += x;
            y0 += y;
        }
        if(select==3)
        {
            x1 += x;
            y1 += y;
        }
        if(x0<x1) { xmin = x0 ; xmax = x1 ; }
        else      { xmin = x1 ; xmax = x0 ; }
        if(y0<y1) { ymin = y0 ; ymax = y1 ; }
        else      { ymin = y1 ; ymax = y0 ; }
    }

    public boolean selectpoint(int x,int y)
    {
        if(x-x0<3 && x-x0>-3 && y-y0<3 && y-y0>-3)
        {
            select = 2;
            return(true);
        }
        else if(x-x1<3 && x-x1>-3 && y-y1<3 && y-y1>-3)
        {
            select = 3;
            return(true);
        }
        return(false);
    }

    public boolean selectobj(int x,int y)
    {
        if(x>=xmin && x<=xmax && y>=ymin && y<=ymax)
        {
            select = 1;
            return(true);
        }
        return(false);
    }

    public boolean selectrect(int xx0,int yy0,int xx1,int yy1)
    {
        if(xx0<x0 && xx1>x1 && yy0<y0 && yy1>y1)
        {
            select = 1;
            return(true);
        }
        return(false);
    }
}

class grobj_poly extends grobj
{
    int x[] = new int[64];
    int y[] = new int[64];
    Color c;
    int n = 0;

    grobj_poly(int xx0,int yy0,Color cc,grobj_list p)
    {
        super(p);
        x[0] = x[1] = xmin = xmax = xx0;
        y[0] = y[1] = ymin = ymax = yy0;
        n = 2;
        select = 3;
        c = cc;
    }

    public void setcolor(Color cc)
    {
        c = cc;
    }

    public grobj copy(grobj_list gl)
    {
        grobj_poly g;

        g = new grobj_poly(x[0],y[0],c,gl);
        g.movepoint(x[1]-x[0],y[1]-y[0]);
        for(int i = 2;i<n;i++)
            g.addpoint(x[i],y[i]);
        g.select = 0;
        return(g);
    }

    public void paint(Graphics g) 
    {
        g.setColor(c);
        Polygon p = new Polygon();
        for(int i=0;i<n;i++)
            p.addPoint(x[i],y[i]);
        g.fillPolygon(p);

        if(select!=0)
        {
            g.setColor(Color.red);
            for(int i = 0;i<n;i++)
            {
                g.drawLine(x[i],y[i],x[(i+1)%n],y[(i+1)%n]);
                g.fillRect(x[i]-1,y[i]-1,3,3);
            }
        }
        else
        {
            g.setColor(Color.black);
            for(int i = 0;i<n;i++)
                g.drawLine(x[i],y[i],x[(i+1)%n],y[(i+1)%n]);
        }
        g.setColor(Color.black);
    }

    public void setminmax()
    {
        xmin = 1000;
        ymin = 1000;
        xmax = -1000;
        ymax = -1000;
        for(int i=0;i<n;i++)
        {
            if(x[i]<xmin) xmin = x[i];
            if(y[i]<ymin) ymin = y[i];
            if(x[i]>xmax) xmax = x[i];
            if(y[i]>ymax) ymax = y[i];
        }
    }

    public void moveobj(int xx,int yy)
    {
        for(int i=0;i<n;i++)
        {
            x[i] += xx;
            y[i] += yy;
        }
        setminmax();
    }

    public void movepoint(int xx,int yy)
    {
        if(select>1)
        {
            x[select-2] += xx;
            y[select-2] += yy;
        }
        setminmax();
    }

    public boolean selectpoint(int xx,int yy)
    {
        for(int i=0;i<n;i++)
        {
            if(xx-x[i]<3 && xx-x[i]>-3 && yy-y[i]<3 && yy-y[i]>-3)
            {
                select = i+2;
                return(true);
            }
        }
        return(false);
    }

    public boolean selectobj(int xx,int yy)
    {
        if(xx>=xmin && xx<=xmax && yy>=ymin && yy<=ymax)
        {
            select = 1;
            return(true);
        }
        return(false);
    }

    public boolean selectrect(int xx0,int yy0,int xx1,int yy1)
    {
        if(xx0<xmin && xx1>xmax && yy0<ymin && yy1>ymax)
        {
            select = 1;
            return(true);
        }
        return(false);
    }

    public void addpoint(int xx,int yy)
    {
        x[n] = xx;
        y[n] = yy;
        select = n+2;
        n++;
        setminmax();
    }

    public boolean done()
    {
        if(x[n-1]==x[n-2] && y[n-1]==y[n-2])
        {
            n--;
            return(true);
        }
        return(false);
    }
}

class grobj_group extends grobj
{
    grobj_list glist;

    grobj_group(grobj_list p,grobj_list q)
    {
        super(p);
        glist = new grobj_list();
        grobj i,ii;
        select = 0;
        for(i = q.ghead;i!=null;i=ii)
        {
            ii = i.next;
            if(i.select!=0)
            {
                i.unlink();
                i.link(glist);
                i.select = 0;
            }
        }
        select = 1;
        xmin = ymin = 1000;
        xmax = ymax = -1000;
        for(i = glist.ghead;i!=null;i=i.next)
        {
            if(i.xmin<xmin) xmin = i.xmin;
            if(i.ymin<ymin) ymin = i.ymin;
            if(i.xmax>xmax) xmax = i.xmax;
            if(i.ymax>ymax) ymax = i.ymax;
        }
    }

    public void ungroup()
    {
        grobj i;

        for(i=glist.ghead;i!=null;i=i.next)
            i.select = 1;
        if(parent.ghead==null)
            parent.ghead = glist.ghead;
        if(parent.gtail!=null)
            parent.gtail.next = glist.ghead;
        if(glist.ghead!=null)
            glist.ghead.prev = parent.gtail;
        parent.gtail = glist.gtail;
        for(i=parent.ghead;i!=null;i=i.next)
            i.parent = parent;

        select = 0;
        unlink();
        parent = null;
        next = null;
        prev = null;
    }

    public grobj copy(grobj_list gl)
    {
        grobj_list alist = new grobj_list();
        grobj i,g;

        for(i=glist.ghead;i!=null;i=i.next)
            i.copy(alist);
        for(i=alist.ghead;i!=null;i=i.next)
            i.select = 1;
        g = new grobj_group(gl,alist);
        return(g);
    }

    public void paint(Graphics g) 
    {
        for(grobj i = glist.ghead;i!=null;i=i.next)
            i.paint(g);
        if(select!=0)
        {
            g.setColor(Color.red);
            g.drawRect(xmin,ymin,xmax-xmin,ymax-ymin);
        }
    }

    public void moveobj(int x,int y)
    {
        grobj i;

        for(i = glist.ghead;i!=null;i=i.next)
            i.moveobj(x,y);
        xmin = ymin = 1000;
        xmax = ymax = -1000;
        for(i = glist.ghead;i!=null;i=i.next)
        {
            if(i.xmin<xmin) xmin = i.xmin;
            if(i.ymin<ymin) ymin = i.ymin;
            if(i.xmax>xmax) xmax = i.xmax;
            if(i.ymax>ymax) ymax = i.ymax;
        }
    }

    public boolean selectpoint(int x,int y)
    {
        return(false);
    }

    public boolean selectobj(int x,int y)
    {
        if(x>=xmin && x<=xmax && y>=ymin && y<=ymax)
        {
            select = 1;
            return(true);
        }
        return(false);
    }

    public boolean selectrect(int xx0,int yy0,int xx1,int yy1)
    {
        if(xx0<xmin && xx1>xmax && yy0<ymin && yy1>ymax)
        {
            select = 1;
            return(true);
        }
        return(false);
    }
}

