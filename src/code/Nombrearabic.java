/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

/**
 *
 * @author info-02
 */



public enum Nombrearabic {
    //nombre simple
    ZERO(0, "صفر"),UN(1, "واحد"),DEUX(2, "إثنان"),TROIX(3, "ثلاثة"),
    QUATRE(4,"أربعة"),CINQ(5, "خمسة"),SIX(6, "ستة"),SEPT(7, "سبعة"),
    HUIT(8,"ثمانية"),NEUF(9, "تسعة"),DIX(10, "عشر"),ONZE(11, "إحداى عشر"), 
    DOUZE(12, "إثنا عشر "),TREIZE(13, "ثلاثة عشر "),QUATORZE(14, "أربعة عشر"),
    QUINZE(15, "خمسة عشر"),SEIZE(16, "ستة عشر"),DIXSEPT(17, "سبعة عشر"),
    DIXHUIT(18, "ثمانية عشر"),DIXNEUF(19, "تسعة عشر"),
   
    //de 20 a 99
    VINGT(20, 29, "عشرون"),
    TRENTE(30, 39, "ثلاثون"),
    QUARANTE(40, 49, "أربعون"), 
    CINQUANTE(50, 59, "خمسون"),
    SOIXANTE(60, 69, "ستون"),
    SOIXANTEDIX (70, 79, "سبعون"),
    QUATREVINGT(80, 89,"ثمانون"),
    QUATREVINGTDIX(90, 99, "تسعون"),
   
    //de 10 a X milliard
    DIXAINE(10, 99),
        CENT(100, 999, "مائة",DIXAINE),

    MILLE(1000, 999999,  "الف", CENT),
    MILLION(1000000,99999999, "مليون", MILLE),
    MILLIARD(1000000000, Long.MAX_VALUE,"مليار", MILLION),
   
    //enum de calcul
    CALCULATE(){
        protected String getValue(long value)throws Exception {
            if (value == 0) return ZERO.label;
            else return ((value < 0) ? "ناقص " : "")+ MILLIARD.getStringValue((Math.abs(value)));
        }

        protected String getValue(double value,String separator,String Cts)throws Exception {
            if (value == 0) return ZERO.label+" "+separator;
                                                 
            else{
                StringBuilder sb = new StringBuilder();
                sb.append((value < 0) ? "ناقص " : "");
                                 
                String vstr = Double.toString(value);
                                
                                 // add by hatem m'hamed amine moslem of algeria 
                                  if (vstr.contains("E")){
                                    // en cas de 10 milion et plus la valeur de vstr est 1.0E7
                                    //cette valeur pose un problème est donne une erreur a l'execution
                                    // car le caracteur E ne pas un nombre de plus 
                                    //le pont des milliard est interpret comme une virgule
                                    //ca fause les calcule
                                    // il faut rendre la valeur comme 10000000000.00
                                    StringBuilder E = new StringBuilder();
                                    E.append(vstr);
                                    // en premier en copie la valeur après le caractère «  E »  jusque a la fin
                                    // cette valeur determine la positon de la virgule
                                    String ES = E.substring(E.indexOf("E")+1);
                                    // en supprimé les  caractères depuis  «  E »  jusque a la fin
                                    E.delete(E.indexOf("E"),E.length());
                                    // en supprime la virgule des milliard que fause les calcule
                                    E.deleteCharAt(E.indexOf(".")); // we have E equale to "10"
                                    Integer EEE = 0;
                                    EEE=EEE.valueOf(ES);
                                    // dans le cas au les zéro en été enlevée en les insert si la longer  de  E est inférieure  au nombre des zéro 
                                     // cas de  E = "10"   avac  longer  = 2  est nombre avant zero EEE = 7  en ajout 6 zero en obtian 10000000
                                    // une boucle de six fois 2,3,4,5,6,7
                                    if (E.length()<= EEE){for (int i =E.length();i< EEE+1;i++){E.append("0");}}
                                    E.insert(EEE+1, ".") ;  
                                    vstr = E.toString(); }
                // fin de add by hatem 
               
                int indexOf = vstr.indexOf('.');
               
                if(indexOf == -1){
                    sb.append(MILLIARD.getStringValue((long)(Math.abs(value))));
                    sb.append(" ");
                    sb.append(separator);
                }else{
                    sb.append(MILLIARD.getStringValue(Long.parseLong(vstr.substring(0, indexOf))));
                    sb.append(" ");
                    sb.append(separator);
                                 String floatting =vstr.substring(indexOf+1,(indexOf+3>=vstr.length())?vstr.length():indexOf+3) +(indexOf+3>vstr.length()?"0":"");

                                                            
                                                                             
                                        long v = Long.parseLong(floatting);
                                        if(v!=0){
                        sb.append(" و ");
                        sb.append(CENT.getStringValue(v));
                                                sb.append(" ");
                                                sb.append(Cts);
                                                
                    }
                }
                return sb.toString();
            }
        }

    };

    protected long min, max;
    protected String label;
    protected Nombrearabic before;
    // valeur � ajout � la fin d'un nombre entier
    private String addMin;
    /* constructeurs*/
    Nombrearabic() {
    }

    Nombrearabic(long v, String s) {
        this(v, v, s);
    }

    Nombrearabic(long min, long max) {
        this.min = min;
        this.max = max;
    }

    Nombrearabic(long min, long max, String label, Nombrearabic before) {
        this(min, max, label);
        this.before = before;
    }

    Nombrearabic(long min, long max, String label,String addMin) {
        this(min, max, label);
        this.addMin = addMin;
    }
   
    Nombrearabic(long min, long max, String label) {
        this(min, max);
        this.label = label;
    }
   
    protected String getValue(long value)throws Exception{
        throw new Exception("Vous devez appeller la methode par l'enumeration Chiffre.CALCULATE");
    }

    protected String getValue(double value,String separator,String Cts)throws Exception{
        throw new Exception("Vous devez appeller la methode par l'enumeration Chiffre.CALCULATE");
    }

    // fonction de transformation
    private String getStringValue(long value) {
        long v1 = value / this.min;
            if (v1 == 0 && before != null)return before.getStringValue(value);
        StringBuilder add = new StringBuilder();
        Nombrearabic[]values = Nombrearabic.values();
        if(value<20) return values[(int)value].label;
        for (int i = 0; i < values.length; i++) {
            Nombrearabic nombre = values[i];
                        
            //si la valeur est inferieur a 100
            if (value < 100 && nombre.min <= value && nombre.max >= value) {
                //cas des valeurs 20, 30, 40, etc...
                if (value == nombre.min) return nombre.label+((nombre.addMin!=null)?nombre.addMin:"");
                else{
                    StringBuilder sb = new StringBuilder();
                                       //first chiffre
                    sb.append(((value - nombre.min > 0) ? DIXAINE.getStringValue(value - nombre.min) : ""));
                                        sb.append(" و ");
                    //second chiffre
                    sb.append(nombre.label);
                                        return sb.toString();}
                          // fin si  la valeur est inferieur a 100 this work very good
                                
                          // cas de valeur plus de 100      
            } else if (nombre.min <= v1 && nombre.max >= v1 && value >= 100) {
                //premiere partie du nombre
                               //100 et 1000 et 1000000 et 1000000000
                if ((this.equals(MILLIARD) ||this.equals(MILLION) ||this.equals(MILLE) || this.equals(CENT))&& (Nombrearabic.UN.equals(nombre)|| Nombrearabic.DEUX.equals(nombre))){
                                   add.append(label);
                                   //200 et 2000 et 2000000 et 2000000000
                                   if (this.equals(CENT)&&Nombrearabic.DEUX.equals(nombre)){ add.deleteCharAt(add.indexOf("ة"));}
                                 
                                   add.append(((this.equals(CENT)&&Nombrearabic.DEUX.equals(nombre)) ? "ت" : ""));
                                   
                                   add.append(((Nombrearabic.DEUX.equals(nombre)) ? "ين ": ""));
                                   
                                      }
                                
                else{
                                      
                    add.append(nombre.getStringValue(v1));
                   
                    //ajout du label si pr�sent
                                        if (this.equals(CENT)) add.deleteCharAt(add.indexOf("ة"));
                                        if (this.equals(CENT)&&Nombrearabic.HUIT.equals(nombre)) add.deleteCharAt(add.indexOf("ي"));
                                        add.append(((this.equals(CENT)&&label != null) ? "" : " "));
                                        
                                        add.append(((label != null) ? label : ""));
                                        
                                        if (this.equals(MILLE)&&(Nombrearabic.TROIX.equals(nombre)||Nombrearabic.QUATRE.equals(nombre)||Nombrearabic.CINQ.equals(nombre)||Nombrearabic.SIX.equals(nombre)||Nombrearabic.SEPT.equals(nombre)||Nombrearabic.HUIT.equals(nombre)||Nombrearabic.NEUF.equals(nombre)||Nombrearabic.DIX.equals(nombre)))add.insert(add.length()-1, "ا");                                               

                                                }
                                   
                                    
                //deuxi�me partie du nombre
                add.append(((value - (v1 * this.min) > 0) ? (" و " + before.getStringValue(value - (v1 * this.min))): ""));
                return add.toString();
            }
        }
        return add.toString();
    }
}
