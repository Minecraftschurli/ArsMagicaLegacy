package minecraftschurli.arsmagicalegacy.api.spellsystem;

import minecraftschurli.arsmagicalegacy.objects.spell.EssenceType;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class EssenceSpellIngredient implements ISpellIngredient {
    public final int amount;
    public final EssenceType essenceType;

    public EssenceSpellIngredient (EssenceType type, int amount){
        this.essenceType = type;
        this.amount = amount;
    }
}