package uk.co.punishell.insideview.model.services.web.commands.entityCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RunnerCommand {

    private Long id;

    private String horseName;

    private boolean status;

    private double price9;
    private double price10;
    private double price11;
    private double mov9to11;
    private double price60;
    private double mov60;
    private double price30;
    private double mov30;
    private double price15;
    private double mov15;
    private double price5;
    private double mov5;
    private double price3;
    private double mov3;
    private double price2;
    private double mov2;
    private double price1;
    private double mov1;
    private double mean;
    private double mov3to1;

    private String result;

    private int cpr;
    private int nptips;
    private int stars;
    private int naps;
}
