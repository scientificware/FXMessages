/*
 * The ScientificWare License
 *
 * Copyright (c) 2020 ScientificWare - Guy Abossolo Foh
 *
 * www.scientificware.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *  Please contact Guy Abossolo Foh, 71 rue Guy de Maupassant 69500 Bron France
 *  or visit www.scientificware.com if you need additional informations or have any
 *  questions.
 */
package com.fxmessages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class FXShortCuts {

    // Déclaration et valeurs par défaut.
    String titleAction = "Action";
    String titleExample ="Exemple";
    String titleCustom = "Perso.";
    private ArrayList<FXShortCut> fxShortCutArrayList = new ArrayList<>();

    String language = "fr";
    boolean usecustom = false;

    HashMap<String, String> hm = new HashMap<>();
    private FileManager fm;

    public static final class FXSCat {
        String LETTRES_GRECQUES_MINUSCULES = "lettres grecques minuscules";
        String LETTRES_GRECQUES_MAJUSCULES ="lettres grecques majuscules";
        String ENSEMBLE_DE_NOMBRES = "ensemble de nombres";
        String OPERATEURS_D_ENSEMBLES = "opérateurs d'ensembles";
        String FORMATS_D_ECRITURE = "formats d'écritures";
        String LETTRES_CERCLEES = "lettres cerclées";
        String MATRICES = "matrices";
    }

    // Méthodes de FXShortCuts

    public void setTitleAction(String titleAction) {
        this.titleAction = titleAction;
    }

    public void setTitleExample(String titleExample) {
        this.titleExample = titleExample;
    }

    public void setTitleCustom(String titleCustom) {
        this.titleCustom = titleCustom;
    }

    public String getTitleAction() {
        return titleAction;
    }

    public String getTitleExample() {
        return titleExample;
    }

    public String getTitleCustom() {
        return titleCustom;
    }

    private void populateHashMap() {

        Iterator<FXShortCut> it = getFXShortCuts().iterator();
        String st = "";
        FXShortCut fxsc;
        while (it.hasNext()) {
            fxsc = it.next();
            st = fxsc.getCustom();
            st = (getUseCustom() && !st.equals("")) ? st : fxsc.getShortCut();
            hm.put(fxsc.getInternal(), st);
            //System.out.println("--------------------- st =" + st + "------------------------ fxsc.getInternal() = " + fxsc.getInternal());
        }
    }

    public void setLanguage(String language) {
        this.language = language;
        translate();
        upDateHashMap();
    }

    public String getLanguage() {
        return language;
    }

    public boolean getUseCustom() {
        return usecustom;
    }

    // Une des deux méthodes est inutile puisque ici rien n'est fait mis à part appeler poplulateHashMap()
    // A simplifier.
    private void upDateHashMap() {
        populateHashMap();
    }

    public HashMap getHashMap(){
        return hm;
    }

    private /*public*/ ArrayList<FXShortCut> getFXShortCuts() {
        return fxShortCutArrayList;
    }

    //Traduction des raccourcis.
    abstract void translate();

    public void setUsecustom(boolean usecustom) {
        this.usecustom = usecustom;
        upDateHashMap();
    }

    public String getHTML(){
        String emHTMLBegin ="<!DOCTYPE html>" +
                "<!-- If you read this message, save that file as a html file, in order to read it correctly -->"+
                "<!--" +
                "    Copyright (c) 2020. Guy Abossolo Foh. All rights reserved." +
                "    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER." +
                "" +
                "    This code is free software; you can redistribute it and/or modify it" +
                "    under the terms of the GNU General Public License version 2 only, as" +
                "    published by the Free Software Foundation." +
                "" +
                "    This code is distributed in the hope that it will be useful, but WITHOUT" +
                "     ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or" +
                "     FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License" +
                "     version 2 for more details (a copy is included in the LICENSE file that" +
                "     accompanied this code)." +
                "" +
                "     You should have received a copy of the GNU General Public License version" +
                "     2 along with this work; if not, write to the Free Software Foundation," +
                "     Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA." +
                "" +
                "     Please contact Guy Abossolo Foh, 71 rue Guy de Maupassant 69500 Bron France" +
                "     or visit www.scientificware.com if you need additional information or have any" +
                "     questions." +
                "  -->" +
                "" +
                "<html>" +
                "" +
                "<head>" +
                "    <meta charset=\"UTF-8\"/>" +
                "    <link rel = \"stylesheet\" href = \"file:///android_asset/css/fx_settings_style.css\">" +
                "    <link rel = \"stylesheet\" href = \"file:///android_asset/css/fx_message_style.css\">" +
                "    <script type=\"text/javascript\" src=\"file:///android_asset/js/fx_settings_scripts.js\"></script>" +
                "    <title>Settings</title>" +
                "    <meta charset=\"UTF-8\">" +
                "</head>" +
                "" +
                "<body>";
        String emHTMLTableBegin ="" +
                "<table><tr>" +
                "        <th>" + getTitleAction() + "</th>" +
                "        <th>" + getTitleExample() + "</th>" +
                "        <th>" +
                "            <select name=\"Language\" id=\"language-select\" onchange=\"setShortCuts()\">" +
                "                <option value=\"fr\">[fr]</option>" +
                "                <option value=\"en\">[en]</option>" +
                "                <option value=\"sp\">[sp]</option>" +
                "                <option value=\"de\">[de]</option>" +
                "                <option value=\"tex\" disabled>[tex]</option>" +
                "                <option value=\"loo\" disabled>[loo]</option>" +
                "            </select>" +
                "        </th>" +
                "        <th>[" + getTitleCustom() + "]</th>" +
                "    </tr>";
        String emHTMLTableEnd ="" +
                "</table>";
        String emHTMLEnd = "" +
                "</body>" +
                "" +
                "</html>";
        StringBuilder pHTML = new StringBuilder(emHTMLBegin);
        pHTML.append(emHTMLTableBegin);
        Iterator<FXShortCut> it = getFXShortCuts().iterator();
        String trows = "";
        FXShortCut fxsc;
        while (it.hasNext()) {
            fxsc = it.next();
            pHTML.append(
                    "    <tr>" +
                    "        <td>" + fxsc.getAction() + "</td>" +
                    "        <td>" + fxsc.getExample() + "</td>" +
                    "        <td>" + fxsc.getShortCut() + "</td>" +
                    "        <td contenteditable=\"true\">" + fxsc.getCustom() + "</td>" +
                    "    </tr>\n");
        }
        pHTML.append(emHTMLTableEnd);
        pHTML.append(emHTMLEnd);
        // Décommenter pour obtenir le listing du fichier html.
        //System.out.println(pHTML);
        return pHTML.toString().replaceFirst("<option value=\"" + getLanguage() + "\">","<option value=\"" + getLanguage() + "\" selected>");
    }


    /**
     * Sous-classe définissant un raccourci.
     */
    public class FXShortCut {
        String example;
        String action;
        String internal;
        String shortcut;
        String category;
        String custom;

        public FXShortCut(String example, String action, String internal, String shortcut, String custom, String category) {
            this.action = action;
            this.internal = internal;
            this.shortcut = shortcut;
            this.custom = custom;
            this.category = category;
            this.example = example;
            putHashMap();
        }

        private void putHashMap() {
            fxShortCutArrayList.add(this);
        }

        public FXShortCut(String example, String internal, String category) {
            this(example, "", internal, "", "", category);
        }

        public void setLocal(String action){
            setLocal(action, "","");
        }

        public void setLocal(String action, String shortcut){
            setLocal(action, shortcut,"");
        }

        public void setLocal(String action, String shortcut, String custom){
            //this.action = action;
            if(!action.equals("")) this.action = action;
            // Si une version locale du raccourci n'est pas définie laisser le raccourcis pas défaut.
            if(!shortcut.equals("")) this.shortcut = shortcut;
            this.custom = custom;
        }

        public String getExample() {
            return example;
        }

        public String getAction() {
            return action;
        }

        public String getInternal() {
            return internal;
        }

        public String getShortCut() {
            return shortcut;
        }

        public String getCategory() {
            return category;
        }

        public String getCustom() {
            return custom;
        }
    }

    // Liste des raccourcis par défaut.
    FXShortCut UN_TIERS = new FXShortCut("\u00b9\u2044\u2083", "un tiers", ".1/3", ".1/3", "", "Format");
    FXShortCut UN_DEMI = new FXShortCut("\u00bd", "un demi", ".1/2", ".1/2", ".1/2", "Format");
    FXShortCut QUATRE_TIERS = new FXShortCut("\u2074\u2044\u2083", "quatre tiers", ".4/3", ".4/3", "", "Format");

    FXShortCut EN_EXPOSANT = new FXShortCut("<math><msup><mrow></mrow><mrow></mrow></msup></math>", "exposant", ".exp", ".exp", "", "Format");
    FXShortCut EXPOSANT = new FXShortCut("<math><msup><mrow></mrow><mrow></mrow></msup></math>", "exposant", ".exp", ".exp", "", "Format");
    FXShortCut EXPOSANT_0 = new FXShortCut("<math><mrow></mrow>\u2070</math>", "exposant 0", ".e0", ".e0", "", "Format");
    FXShortCut EXPOSANT_P1 = new FXShortCut("<math><mrow></mrow>\u00b9</math>", "exposant 1", ".e1", ".e1", "", "Format");
    FXShortCut EXPOSANT_M1 = new FXShortCut("<math><mrow></mrow>\u207b\u00b9</math>", "exposant -1", ".e-1", ".e-1", "", "Format");
    FXShortCut EXPOSANT_N = new FXShortCut("<math><mrow></mrow>\u207f</math>", "exposant n", ".^n", ".^n", "", "Format");
    FXShortCut EXPOSANT_M2 = new FXShortCut("<math><mrow></mrow>\u207b²</math>", "exposant -2", ".e-2", ".e-2", "", "Format");

    FXShortCut INDICE_N = new FXShortCut("<math><mrow></mrow>\u2099</math>", "indice n", "._n", "._n", "", "Format");
    FXShortCut EN_INDICE = new FXShortCut("<math><msub><mrow></mrow><mrow></mrow></msub></math>", "Indice", ".ind", ".ind", "", "Format");
    FXShortCut INDICE = new FXShortCut("<math><msub><mrow></mrow><mrow></mrow></msub></math>", "Indice", ".ind", ".ind", "", "Format");
    FXShortCut EN_INDICE_ET_EXPOSANT = new FXShortCut("<math><msubsup><mrow></mrow><mrow></mrow><mrow></mrow></msubsup></math>", "Indice et exposant", ".inex", ".inex", "", "Format");
    FXShortCut INDICE_ET_EXPOSANT = new FXShortCut("<math><msubsup><mrow></mrow><mrow></mrow><mrow></mrow></msubsup></math>", "Indice et exposant", ".inex", ".inex", "", "Format");
    // FXShortCut TABLE = new FXShortCut("e","Insert a table",".tbl",".tbl","","Format");
    FXShortCut PLUS = new FXShortCut("+", "plus", ".plus", ".plus", ".pl", "Carac");
    FXShortCut MOINS = new FXShortCut("-", "moins", ".moins", ".moins", ".mo", "Carac");
    FXShortCut FOIS = new FXShortCut("×", "fois", ".fois", ".fois", ".fo", "Carac");
    FXShortCut DIVISE_PAR = new FXShortCut("÷", "divisé par", ".dp", ".dp", "", "Carac");
    FXShortCut RACINE_CARREE = new FXShortCut("<math><msqrt><mrow></mrow></msqrt></math>", "Racine carrée d'un nombre", ".rac2", ".rac2", "", "Format");
    FXShortCut RADICAL = new FXShortCut("√", "Insert √", ".rad", ".rad", "", "Format");
    FXShortCut FRACTION = new FXShortCut("<math><mfrac><mrow></mrow><mrow></mrow></mfrac></math>", "Fraction", ".frac", ".frac", "", "Carac");
    FXShortCut SUR = new FXShortCut("<math><mfrac><mrow></mrow><mrow></mrow></mfrac></math>", "Sur", ".sur", ".sur", "", "Carac");
    FXShortCut INFINI = new FXShortCut("∞", "infini", ".inf", ".inf", "", "Carac");
    FXShortCut REPRESENTE = new FXShortCut("≙", "représente", ".=^", ".=^", "", "Carac");
    FXShortCut EST_EGAL_A = new FXShortCut("=", "est égal à", ".eg", ".eg", "", "Carac");
    FXShortCut INFERIEUR_OU_EGAL_A = new FXShortCut("<math><mo>≤</mo></math>", "Inférieur ou égal à", ".ieg", ".ieg", "", "Carac");
    FXShortCut SUPERIEUR_OU_EGAL_A = new FXShortCut("<math><mo>≥</mo></math>", "Supérieur ou égal à ≥", ".seg", ".seg", "", "Carac");
    FXShortCut PLUS_MOINS = new FXShortCut("±", "plus moins", ".+-", ".+-", "", "Carac");
    FXShortCut MOINS_PLUS = new FXShortCut("∓", "moins plus", ".-+", ".-+", "", "Carac");
    FXShortCut EST_APPROXIMATIVEMENT_EGAL_A = new FXShortCut("≈", "approx. égal à", ".~~", ".~~", "", "Carac");
    FXShortCut EST_ASSOCIE_A = new FXShortCut("<math><mo>↦</mo></math>", "associe à", ".ass", ".ass", "", "Carac");
    FXShortCut FLECHE_GAUCHE = new FXShortCut("←", "flèche gauche", ".<-", ".<-", "", "Carac");
    FXShortCut FLECHE_DROITE = new FXShortCut("→", "flèche droite", ".->", ".->", "", "Carac");
    FXShortCut FLECHE_A_DOUBLE_SENS = new FXShortCut("↔", "Insert ↔", "..<->", "..<->", "", "Carac");
    FXShortCut IMPLIQUE = new FXShortCut("<math><mo>⇒</mo></math>", "implique", ".imp", ".imp", "", "Carac");
    FXShortCut SEULEMENT_SI = new FXShortCut("<math><mo>⇐</mo></math>", "seulement si", ".ssi", ".ssi", "", "Carac");
    FXShortCut SI_ET_SEULEMENT_SI = new FXShortCut("<math><mo>⇔</mo></math>", "Si et seulement si", ".sissi", ".sissi", "..<=>", "Carac");
    FXShortCut NOMBRE_00_CERCLE = new FXShortCut("⓪", "0 cerclé", ".0@", ".0@", "", "Carac");
    FXShortCut NOMBRE_01_CERCLE = new FXShortCut("①", "1 cerclé", ".1@", ".1@", "", "Carac");
    FXShortCut NOMBRE_02_CERCLE = new FXShortCut("②", "2 cerclé", ".2@", ".2@", "", "Carac");
    FXShortCut NOMBRE_03_CERCLE = new FXShortCut("③", "3 cerclé", ".3@", ".3@", "", "Carac");
    FXShortCut NOMBRE_04_CERCLE = new FXShortCut("④", "4 cerclé", ".4@", ".4@", "", "Carac");
    FXShortCut NOMBRE_05_CERCLE = new FXShortCut("⑤", "5 cerclé", ".5@", ".5@", "", "Carac");
    FXShortCut NOMBRE_06_CERCLE = new FXShortCut("⑥", "6 cerclé", ".6@", ".6@", "", "Carac");
    FXShortCut NOMBRE_07_CERCLE = new FXShortCut("⑦", "7 cerclé", ".7@", ".7@", "", "Carac");
    FXShortCut NOMBRE_08_CERCLE = new FXShortCut("⑧", "8 cerclé", ".8@", ".8@", "", "Carac");
    FXShortCut NOMBRE_09_CERCLE = new FXShortCut("⑨", "9 cerclé", ".9@", ".9@", "", "Carac");
    FXShortCut NOMBRE_10_CERCLE = new FXShortCut("⑩", "10 cerclé", ".10@", ".10@", "", "Carac");
    // Racourcis restant à implémenter ⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳
    FXShortCut ANGLE = new FXShortCut("∢", "Insert ∢", ".<)", ".<)", "", "Carac");
    FXShortCut NORME = new FXShortCut("∥", "Insert ∥", ".||", ".||", "", "Carac");
    FXShortCut ENSEMBLE_VIDE = new FXShortCut("<math><mo>∅</mo></math>", "ensemble vide", ".ensvide", ".ensvide", "0/", "Carac");
    FXShortCut DIVISE = new FXShortCut("|", "divise", ".div", ".div", "", "Carac");
    FXShortCut NE_DIVISE_PAS = new FXShortCut("∤", "ne divise pas", ".ndiv", ".ndiv", "", "Carac");
    FXShortCut EST_EQUIVALENT_A = new FXShortCut("≡", "Insert ≡", ".=-", ".=-", "", "Carac");
    FXShortCut ENSEMBLE_ENTIERS_NATURELS = new FXShortCut("<math><mi>ℕ</mi></math>", "ensemble des entiers naturels", ".NN", ".NN", "", "Carac");
    FXShortCut ENSEMBLE_ENTIERS_RELATIFS = new FXShortCut("<math><mi>ℤ</mi></math>", "ensemble des entiers relatifs", ".ZZ", ".ZZ", "", "Carac");
    FXShortCut ENSEMBLE_RATIONNELS = new FXShortCut("<math><mi>ℚ</mi></math>", "ensemble des rationnels", ".QQ", ".QQ", "", "Carac");
    FXShortCut ENSEMBLE_REELS = new FXShortCut("<math><mi>ℝ</mi></math>", "ensemble des réels", ".RR", ".RR", "", "Carac");
    FXShortCut ENSEMBLE_COMPLEXES = new FXShortCut("<math><mi>ℂ</mi></math>", "ensemble des complexes", ".CC", ".CC", "", "Carac");
    FXShortCut ENSEMBLE_REELS_OU_COMPLEXES = new FXShortCut("<math><mi>𝕂</mi></math>", "ensemble des réels ou complexes", ".KK", ".KK", "", "Carac");
    FXShortCut PARENTHESE_DROITE = new FXShortCut("(", "Insert (", ".(", ".(", ".op", "Carac");
    FXShortCut PARENTHESE_GAUCHE = new FXShortCut(")", "Insert )", ".)", ".)", "", "Carac");
    FXShortCut ACCOLADE_DROITE = new FXShortCut("{", "Insert {", ".{", ".{", "", "Carac");
    FXShortCut ACCOLADE_GAUCHE = new FXShortCut("}", "Insert }", ".}", ".}", "", "Carac");
    FXShortCut CROCHET_DROIT = new FXShortCut("[", "Insert [", ".[", ".[", "", "Carac");
    FXShortCut CROCHET_GAUCHE = new FXShortCut("]", "Insert ]", ".]", ".]", "", "Carac");
    FXShortCut ACCOLADE_SUPERIEURE = new FXShortCut("⏞", "Insert ⏞", ".hb", ".acs", "", "Carac");
    FXShortCut EST_DIFFERENT_DE = new FXShortCut("≠", "différent de", ".diff", ".diff", "", "Carac");
    FXShortCut TRES_SUPERIEUR_A = new FXShortCut("≫", "très supérieur à", ".>>", ".>>", "", "Carac");
    FXShortCut STRICTEMENT_EQUIVALENT_A = new FXShortCut("≣", "Insert ≣", "..=--", "..=--", "", "Carac");
    FXShortCut TRES_INFERIEUR_A = new FXShortCut("≪", "très inférieur à", ".<<", ".<<", "", "Carac");
    FXShortCut EST_INCLUS_DANS = new FXShortCut("<math><mi>⊂</mi></math>", "inclus dans", ".inc", ".inc", "", "Carac");
    FXShortCut CONTIENT = new FXShortCut("⊃", "contient", ".co", ".co", "", "Carac");
    FXShortCut EGAL_CERCLE = new FXShortCut("⊜", "Insert ⊜", ".=@", ".=@", "", "Carac");
    FXShortCut ETOILE_CERCLE = new FXShortCut("⊛", "Insert ⊛", ".*@", ".*@", "", "Carac");
    FXShortCut ROND_CERCLE = new FXShortCut("⊚", "Insert ⊚", ".°@", ".°@", "", "Carac");
    FXShortCut POINT_CERCLE = new FXShortCut("⊙", "Insert ⊙", ".@", ".@", "", "Carac");
    FXShortCut SOMME_DIRECTE = new FXShortCut("<math><mo>⨁</mo></math>", "somme directe", ".sdir", ".sdir", "", "Carac");
    FXShortCut FOIS_CERCLE = new FXShortCut("⊗", "Insert ⊗", "..fois@", "..fois@", "", "Carac");
    FXShortCut APPARTIENT_A = new FXShortCut("<math><mo>∈</mo></math>", "appartient à", ".app", ".app", "", "Carac");
    FXShortCut N_APPARTIENT_PAS_A = new FXShortCut("<math><mo>∉</mo></math>", "n'appartient pas à", ".napp", ".napp", "", "Carac");
    FXShortCut QUEL_QUE_SOIT = new FXShortCut("<math><mo>∀</mo></math>", "quel que soit", ".qqs", ".qqs", "", "Carac");
    FXShortCut DERIVEE_PARTIELLE = new FXShortCut("<math><mo>∂</mo></math>", "dérivée partielle", ".dr", ".dr", "", "Format");
    FXShortCut IL_EXISTE = new FXShortCut("<math><mo>∃</mo></math>", "il existe", ".iex", ".iex", "", "Carac");
    FXShortCut POUR_MILLE = new FXShortCut("‰", "pour mille", ".%0", ".%0", "", "Carac");
    FXShortCut INTER = new FXShortCut("<math><mi>∩</mi></math>", "inter", ".inter", ".inter", "", "Carac");
    FXShortCut UNION = new FXShortCut("<math><mi>∪</mi></math>", "union", ".uni", ".uni", "", "Carac");
    FXShortCut INTEGRALE = new FXShortCut("<math><msubsup><mo>&int;</mo><mrow></mrow><mrow></mrow></msubsup><mrow></mrow></math>", "intégrale", ".integ", ".integ", "", "Carac");
    FXShortCut SOMME = new FXShortCut("∑", "somme", ".som", ".som", "", "Carac");
    FXShortCut PRODUIT = new FXShortCut("∏", "produit", ".pro", ".pro", "", "Carac");
    FXShortCut INTEGRALE_CIRCULAIRE = new FXShortCut("∮", "intégrale circulaire", ".oint", ".oint", "", "Carac");
    FXShortCut NON = new FXShortCut("¬", "non", ".non", ".non", "", "Carac");
    FXShortCut ET = new FXShortCut("∧", "et", ".etl", ".etl", "", "Carac");
    FXShortCut OU = new FXShortCut("∨", "ou", ".ou", ".ou", "", "Carac");
    FXShortCut ALPHA_MINUSCULE = new FXShortCut("α", "alpha", ".al", ".al", "", "Carac");
    FXShortCut BETA_MINUSCULE = new FXShortCut("β", "bêta", ".be", ".be", "", "Carac");
    FXShortCut GAMMA_MINUSCULE = new FXShortCut("γ", "gamma", ".ga", ".ga", "", "Carac");
    FXShortCut DELTA_MINUSCULE = new FXShortCut("δ", "delta", ".de", ".de", "", "Carac");
    FXShortCut EPSILON_MINUSCULE = new FXShortCut("ε", "epsilon", ".ep", ".ep", "", "Carac");
    FXShortCut DZETA_MINUSCULE = new FXShortCut("ζ", "dzêta", ".dz", ".dz", "", "Carac");
    FXShortCut ETA_MINUSCULE = new FXShortCut("η", "êta", ".eta", ".eta", "", "Carac");
    FXShortCut THETA_MINUSCULE = new FXShortCut("θ", "thêta", ".th", ".th", "", "Carac");
    FXShortCut IOTA_MINUSCULE = new FXShortCut("ι", "iota ι", ".io", ".io", "", "Carac");
    FXShortCut KAPPA_MINUSCULE = new FXShortCut("κ", "kappa", ".ka", ".ka", "", "Carac");
    FXShortCut LAMBDA_MINUSCULE = new FXShortCut("λ", "lambda", ".la", ".la", "", "Carac");
    FXShortCut MU_MINUSCULE = new FXShortCut("μ", "mu", ".mu", ".mu", "", "Carac");
    FXShortCut NU_MINUSCULE = new FXShortCut("ν", "nu", ".nu", ".nu", "", "Carac");
    FXShortCut XI_MINUSCULE = new FXShortCut("ξ", "xi", ".xi", ".xi", "", "Carac");
    FXShortCut OMICRON_MINUSCULE = new FXShortCut("ο", "omicron", ".omi", ".omi", "", "Carac");
    FXShortCut PI_MINUSCULE = new FXShortCut("π", "pi", ".pi", ".pi", "", "Carac");
    FXShortCut RHO_MINUSCULE = new FXShortCut("ρ", "rhô", ".rh", ".rh", "", "Carac");
    FXShortCut SIGMA_MINUSCULE = new FXShortCut("σ", "sigma", ".sig", ".sig", "", "Carac");
    FXShortCut TAU_MINUSCULE = new FXShortCut("τ", "tau", ".ta", ".ta", "", "Carac");
    FXShortCut UPSILON_MINUSCULE = new FXShortCut("υ", "upsilon", ".up", ".up", "", "Carac");
    FXShortCut PHI_MINUSCULE = new FXShortCut("φ", "phi", ".ph", ".ph", "", "Carac");
    FXShortCut CHI_MINUSCULE = new FXShortCut("χ", "khi", ".kh", ".kh", "", "Carac");
    FXShortCut PSI_MINUSCULE = new FXShortCut("ψ", "psi", ".ps", ".ps", "", "Carac");
    FXShortCut OMEGA_MINUSCULE = new FXShortCut("ω", "oméga", ".ome", ".ome", "", "Carac");
    FXShortCut ALPHA_MAJUSCULE = new FXShortCut("Α", "Αlpha", ".Al", ".Al", "", "Carac");
    FXShortCut BETA_MAJUSCULE = new FXShortCut("Β", "Βêta", ".Be", ".Be", "", "Carac");
    FXShortCut GAMMA_MAJUSCULE = new FXShortCut("Γ", "Gamma", ".Ga", ".Ga", "", "Carac");
    FXShortCut DELTA_MAJUSCULE = new FXShortCut("Δ", "Delta", ".De", ".De", "", "Carac");
    FXShortCut EPSILON_MAJUSCULE = new FXShortCut("Ε", "Εpsilon", ".Ep", ".Ep", "", "Carac");
    FXShortCut DZETA_MAJUSCULE = new FXShortCut("Ζ", "Dzêta", ".Dz", ".Dz", "", "Carac");
    FXShortCut ETA_MAJUSCULE = new FXShortCut("Η", "Êta", ".Eta", ".Eta", "", "Carac");
    FXShortCut THETA_MAJUSCULE = new FXShortCut("Θ", "Thêta", ".Th", ".Th", "", "Carac");
    FXShortCut IOTA_MAJUSCULE = new FXShortCut("Ι", "Ιota", ".Io", ".Io", "", "Carac");
    FXShortCut KAPPA_MAJUSCULE = new FXShortCut("Κ", "Κappa", ".Ka", ".Ka", "", "Carac");
    FXShortCut LAMBDA_MAJUSCULE = new FXShortCut("Λ", "Lambda", ".La", ".La", "", "Carac");
    FXShortCut MU_MAJUSCULE = new FXShortCut("Μ", "Μu", ".Mu", ".Mu", "", "Carac");
    FXShortCut NU_MAJUSCULE = new FXShortCut("Ν", "Νu", ".Nu", ".Nu", "", "Carac");
    FXShortCut XI_MAJUSCULE = new FXShortCut("Ξ", "Xi", ".Xi", ".Xi", "", "Carac");
    FXShortCut OMICRON_MAJUSCULE = new FXShortCut("Ο", "Οmicron", ".Omi", ".Omi", "", "Carac");
    FXShortCut PI_MAJUSCULE = new FXShortCut("Π", "Pi", ".Pi", ".Pi", "", "Carac");
    FXShortCut RHO_MAJUSCULE = new FXShortCut("Ρ", "Rhô", ".Rh", ".Rh", "", "Carac");
    FXShortCut SIGMA_MAJUSCULE = new FXShortCut("Σ", "Sigma", ".Sig", ".Sig", "", "Carac");
    FXShortCut TAU_MAJUSCULE = new FXShortCut("Τ", "Τau", ".ta", ".ta", "", "Carac");
    FXShortCut UPSILON_MAJUSCULE = new FXShortCut("Υ", "Upsilon", ".Up", ".Up", "", "Carac");
    FXShortCut PHI_MAJUSCULE = new FXShortCut("Φ", "Phi", ".Ph", ".Ph", "", "Carac");
    FXShortCut CHI_MAJUSCULE = new FXShortCut("Χ", "Khi", ".Kh", ".Kh", "", "Carac");
    FXShortCut PSI_MAJUSCULE = new FXShortCut("Ψ", "Psi", ".Ps", ".Ps", "", "Carac");
    FXShortCut OMEGA_MAJUSCULE = new FXShortCut("Ω", "Oméga", ".Ome", ".Ome", "", "Carac");
    FXShortCut AU_CARRE = new FXShortCut("&#9634;²", "Carré d'un nombre", ".ca", ".ca", "", "Carac");
    FXShortCut AU_CUBE = new FXShortCut("&#9634;&#x00b3", "Cube d'un nombre", ".cu", ".cu", "", "Carac");
    FXShortCut ROND = new FXShortCut("<math><mo>∘</mo></math>", "Rond", ".rond", ".rond", "", "Carac");
    FXShortCut I = new FXShortCut("<math><mi>ⅈ</mi></math>", "Imaginaire pur", ".ii", ".ii", "", "Carac");
    FXShortCut J = new FXShortCut("<math><mi>ⅉ</mi></math>", "Imaginaire pur", ".jj", ".jj", "", "Carac");
    FXShortCut MATRICE_UNITE_D_ORDRE_2 = new FXShortCut("<math><mo>(</mo><mtable><mtr><mtd><mn>1</mn></mtd><mtd><mn>0</mn></mtd></mtr><mtr><mtd><mn>0</mn></mtd><mtd><mn>1</mn></mtd></mtr></mtable><mo>)</mo></math>", "Matrice unité d'ordre 2", ".I2", ".I2", "", "Carac");
    FXShortCut MATRICE_UNITE_D_ORDRE_3 = new FXShortCut("<math><mo>(</mo><mtable><mtr><mtd><mn>1</mn></mtd><mtd><mn>0</mn></mtd><mtd><mn>0</mn></mtd></mtr><mtr><mtd><mn>0</mn></mtd><mtd><mn>1</mn></mtd><mtd><mn>0</mn></mtd></mtr><mtr><mtd><mn>0</mn></mtd><mtd><mn>0</mn></mtd><mtd><mn>1</mn></mtd></mtr></mtable><mo>)</mo></math>", "Matrice unité d'ordre 3", ".I3", ".I3", "", "Carac");
    FXShortCut MATRICE_UNITE_D_ORDRE_N = new FXShortCut("<math><mo>(</mo><mtable><mtr><mtd><mn>1</mn></mtd><mtd><mn>0</mn></mtd><mtd><mn>⋯</mn></mtd><mtd><mn>0</mn></mtd></mtr><mtr><mtd><mn>0</mn></mtd>" +
            "<mtd><mn>1</mn></mtd><mtd><mn>⋯</mn></mtd><mtd><mn>0</mn></mtd></mtr><mtr><mtd><mn>⋮</mn></mtd><mtd><mn>⋮</mn></mtd><mtd><mn>⋱</mn></mtd><mtd><mn>⋮</mn></mtd></mtr><mtr>" +
            "<mtd><mn>0</mn></mtd><mtd><mn>0</mn></mtd><mtd><mn>1</mn></mtd><mtd><mn>1</mn></mtd></mtr></mtable><mo>)</mo></math>","matrice unité d'ordre n",".In",".In","","Carac");
    FXShortCut MATRICE_CARREE_D_ORDRE_2 = new FXShortCut("<math><mo>(</mo><mtable><mtr><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd></mtr><mtr><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd></mtr></mtable><mo>)</mo></math>", "Matrice vide d'orde 2", ".MC2", ".MC2", "", "Carac");
    FXShortCut MATRICE_CARREE_D_ORDRE_3 = new FXShortCut("<math><mo>(</mo><mtable><mtr><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd></mtr><mtr><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd></mtr><mtr><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd></mtr></mtable><mo>)</mo></math>", "Matrice vide d'orde 3", ".MC3", ".MC3", "", "Carac");
    FXShortCut MATRICE_CARREE_D_ORDRE_N = new FXShortCut("<math><mo>(</mo><mtable><mtr><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd><mtd><mn>⋯</mn></mtd><mtd><mrow></mrow></mtd></mtr><mtr><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd><mtd><mn>⋯</mn></mtd><mtd><mrow></mrow></mtd></mtr><mtr><mtd><mn>⋮</mn></mtd><mtd><mn>⋮</mn></mtd><mtd><mn>⋱</mn></mtd><mtd><mn>⋮</mn></mtd></mtr><mtr><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd><mtd><mrow></mrow></mtd></mtr></mtable><mo>)</mo></math>", "Matrice carrée d'ordre n", ".MCn", ".MCn", "", "Carac");

}
