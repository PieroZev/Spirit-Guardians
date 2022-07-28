package com.piero.spiritguardians.Cards;

import android.graphics.drawable.Drawable;

import com.piero.spiritguardians.R;

import java.util.ArrayList;

public class Deck1 {

    private CardNames cartas = new CardNames();
    private Images imagenes = new Images();

    //3 ataque3
    private Card ataque3_1 = new Card(1,3,0,5, imagenes.getImg_ataque3(), cartas.getTxt_ataque3());
    private Card ataque3_2 = new Card(1,3,0,5, imagenes.getImg_ataque3(), cartas.getTxt_ataque3());
    private Card ataque3_3 = new Card(1,3,0,5, imagenes.getImg_ataque3(), cartas.getTxt_ataque3());
    //3 escudo
    private Card escudo_1 = new Card(2,0,5,3, imagenes.getImg_escudo(), cartas.getTxt_escudo());
    private Card escudo_2 = new Card(2,0,5,3, imagenes.getImg_escudo(), cartas.getTxt_escudo());
    private Card escudo_3 = new Card(2,0,5,3, imagenes.getImg_escudo(), cartas.getTxt_escudo());
    //2 Obreros
    private Card obrero_1 = new Card(3,0,0,8, imagenes.getImg_obreros(), cartas.getTxt_obreros());
    private Card obrero_2 = new Card(3,0,0,8, imagenes.getImg_obreros(), cartas.getTxt_obreros());
    //2 Soldados
    private Card soldado_1 = new Card(4,0,0,8, imagenes.getImg_soldados(), cartas.getTxt_soldados());
    private Card soldado_2 = new Card(4,0,0,8, imagenes.getImg_soldados(), cartas.getTxt_soldados());
    //2 Magia
    private Card magia_1 = new Card(5,0,0,8, imagenes.getImg_magia(), cartas.getTxt_magia());
    private Card magia_2 = new Card(5,0,0,8, imagenes.getImg_magia(), cartas.getTxt_magia());
    //2 Economía
    private Card economia_1 = new Card(6,0,0,5, imagenes.getImg_economia(), cartas.getTxt_economia());
    private Card economia_2 = new Card(6,0,0,5, imagenes.getImg_economia(), cartas.getTxt_economia());
    //1 Maldición
    private Card maldicion_1 = new Card(7,0,0,25, imagenes.getImg_maldicion(), cartas.getTxt_maldicion());
    //3 ladrón
    private Card ladron_1 = new Card(8,0,0,12, imagenes.getImg_ladron(), cartas.getTxt_ladron());
    private Card ladron_2 = new Card(8,0,0,12, imagenes.getImg_ladron(), cartas.getTxt_ladron());
    private Card ladron_3 = new Card(8,0,0,12, imagenes.getImg_ladron(), cartas.getTxt_ladron());
    //2 ataque5
    private Card ataque5_1 = new Card(9,5,0,8, imagenes.getImg_ataque5(), cartas.getTxt_ataque5());
    private Card ataque5_2 = new Card(9,5,0,8, imagenes.getImg_ataque5(), cartas.getTxt_ataque5());
    //2 ataque10
    private Card ataque10_1 = new Card(10,10,0,15, imagenes.getImg_ataque10(), cartas.getTxt_ataque10());
    private Card ataque10_2 = new Card(10,10,0,15, imagenes.getImg_ataque10(), cartas.getTxt_ataque10());
    //2 escudo10
    private Card escudo10_1 = new Card(11,0,10,5, imagenes.getImg_escudo10(), cartas.getTxt_escudo10());
    private Card escudo10_2 = new Card(11,0,10,5, imagenes.getImg_escudo10(), cartas.getTxt_escudo10());
    //3 Bancarrota
    private Card bancarrota_1 = new Card(12,0,0,15, imagenes.getImg_bancarrota(), cartas.getTxt_bancarrota());
    private Card bancarrota_2 = new Card(12,0,0,15, imagenes.getImg_bancarrota(), cartas.getTxt_bancarrota());
    private Card bancarrota_3 = new Card(12,0,0,15, imagenes.getImg_bancarrota(), cartas.getTxt_bancarrota());
    //3 Muralla
    private Card muralla_1 = new Card(13,0,20,15, imagenes.getImg_muralla(), cartas.getTxt_muralla());
    private Card muralla_2 = new Card(13,0,20,15, imagenes.getImg_muralla(), cartas.getTxt_muralla());
    private Card muralla_3 = new Card(13,0,20,15, imagenes.getImg_muralla(), cartas.getTxt_muralla());
    //3 Quita Muralla
    private Card quita_muralla_1 = new Card(14,0,0,10, imagenes.getImg_quita_muralla(), cartas.getTxt_quita_muralla());
    private Card quita_muralla_2 = new Card(14,0,0,10, imagenes.getImg_quita_muralla(), cartas.getTxt_quita_muralla());
    private Card quita_muralla_3 = new Card(14,0,0,10, imagenes.getImg_quita_muralla(), cartas.getTxt_quita_muralla());
    //2 Castillo
    private Card castillo_1 = new Card(15,0,24,20, imagenes.getImg_castillo(), cartas.getTxt_castillo());
    private Card castillo_2 = new Card(15,0,24,20, imagenes.getImg_castillo(), cartas.getTxt_castillo());
    //2 Muerte
    private Card muerte_1 = new Card(16,28,0,20, imagenes.getImg_muerte(), cartas.getTxt_muerte());
    private Card muerte_2 = new Card(16,28,0,20, imagenes.getImg_muerte(), cartas.getTxt_muerte());
    //2 Dragon
    private Card dragon_1 = new Card(17,25,0,20, imagenes.getImg_dragon(), cartas.getTxt_dragon());
    private Card dragon_2 = new Card(17,25,0,20, imagenes.getImg_dragon(), cartas.getTxt_dragon());
    //1 Babilonia
    private Card babilonia_1 = new Card(18,0,0,25, imagenes.getImg_babilonia(), cartas.getTxt_babilonia());

    public ArrayList<Card> getDeck(){

        ArrayList<Card> deck = new ArrayList<>();
        deck.add(ataque3_1);
        deck.add(ataque3_2);
        deck.add(ataque3_3);
        deck.add(escudo_1);
        deck.add(escudo_2);
        deck.add(escudo_3);
        deck.add(ataque5_1);
        deck.add(ataque5_2);
        deck.add(ataque10_1);
        deck.add(ataque10_2);
        deck.add(escudo10_1);
        deck.add(escudo10_2);
        deck.add(obrero_1);
        deck.add(obrero_2);
        deck.add(soldado_1);
        deck.add(soldado_2);
        deck.add(magia_1);
        deck.add(magia_2);
        deck.add(maldicion_1);
        deck.add(economia_1);
        deck.add(economia_2);
        deck.add(ladron_1);
        deck.add(ladron_2);
        deck.add(ladron_3);
        deck.add(bancarrota_1);
        deck.add(bancarrota_2);
        deck.add(bancarrota_3);
        deck.add(muralla_1);
        deck.add(muralla_2);
        deck.add(muralla_3);
        deck.add(quita_muralla_1);
        deck.add(quita_muralla_2);
        deck.add(quita_muralla_3);
        deck.add(dragon_1);
        deck.add(dragon_2);
        deck.add(muerte_1);
        deck.add(muerte_2);
        deck.add(castillo_1);
        deck.add(castillo_2);
        deck.add(babilonia_1);

        return deck;
    }
}

