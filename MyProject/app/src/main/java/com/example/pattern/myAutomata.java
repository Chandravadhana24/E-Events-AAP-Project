package com.example.pattern;

import java.util.ArrayList;

public class myAutomata {
    ArrayList< Integer > input =new ArrayList<Integer> ();

    public String pattern;
    public String state;
    public String in;
    public String transition;
    public String initial;
    public String fin;


    public myAutomata(String pattern) {
        this.input.add(0);
        this.input.add(1);
        this.input.add(2);
        this.input.add(3);
        this.input.add(4);
        this.input.add(5);
        this.input.add(6);
        this.input.add(7);
        this.input.add(8);
        this.pattern = pattern;
    }

    public void automata()  {
        if(pattern.length()>0)
        {
            ArrayList< String > states =new ArrayList<String> ();
            int i,j,k,n;
            n=this.pattern.length();
            this.state="States:{ ";
            for(i=0;i<(this.pattern.length())+2;i++)
            {
                states.add('q'+(Integer.toString(i)));
                this.state+=('q'+(Integer.toString(i)));
                if(i!=n+1)
                    this.state+=(",");

            }

            this.state+=" }";
            this.in="Input: { ";

            for(i=0;i<9;i++)
            {
                this.in+=((Integer.toString(i)));
                if(i!=8)
                    this.in+=(",");
            }

            this.in+=(" }");

            k=0;

            this.transition="";
            int m;
            for(i=0;i<n+2;i++)
            {
                this.transition+=(states.get(i)+" : { [");
                if(k<n)
                {
                    m=0;
                    this.transition+=((this.pattern.charAt(k) -'0' ) + "]:" + states.get(i+1)+" , [");
                    for(j=0;j<9;j++)
                    {
                        if(j!=((this.pattern.charAt(k)-'0')))
                        {
                            this.transition+=(Integer.toString(j));
                            if(m<7)
                            {
                                this.transition+=",";
                                m++;
                            }
                        }
                    }

                    this.transition+=("]:"+states.get(n+1)+"}\n");

                    k++;

                }
                else
                {
                    m=0;
                    for(j=0;j<9;j++)
                    {
                        this.transition+=(Integer.toString(j));
                        if(m<8)
                        {
                            this.transition+=",";
                            m++;
                        }
                    }

                    this.transition+=("]:"+states.get(n+1)+"}\n");
                }


            }

            this.initial=("Initial State:"+states.get(0));
            this.fin=("Final State:"+states.get(n));

        }

    }

}
