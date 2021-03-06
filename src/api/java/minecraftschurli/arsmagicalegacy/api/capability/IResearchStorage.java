package minecraftschurli.arsmagicalegacy.api.capability;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.registry.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public interface IResearchStorage {
    int get(int tier);

    boolean use(int tier, int count);

    default boolean use(int tier) {
        return use(tier, 1);
    }

    default void add(int tier) {
        add(tier, 1);
    }

    void add(int tier, int count);

    void set(int tier, int count);

    default void setFrom(IResearchStorage old) {
        for (SkillPoint type : SkillPointRegistry.SKILL_POINT_REGISTRY.values().stream().filter(SkillPoint::canRender).collect(Collectors.toList())) set(type.getTier(), old.get(type.getTier()));
        forgetAll();
        old.getLearnedSkills().forEach(this::learn);
    }

    List<Skill> getLearnedSkills();

    List<ResourceLocation> getLearned();

    default void learn(@Nonnull Skill skill) {
        learn(skill.getRegistryName());
    }

    default void forget(@Nonnull Skill skill) {
        forget(skill.getRegistryName());
    }

    void learn(@Nonnull ResourceLocation location);

    void forget(@Nonnull ResourceLocation location);

    void forgetAll();

    default boolean knows(Skill skill) {
        return knows(skill.getRegistryName());
    }

    boolean knows(ResourceLocation skill);

    boolean canLearn(Skill skill);

    Map<Integer, Integer> getPoints();
}
