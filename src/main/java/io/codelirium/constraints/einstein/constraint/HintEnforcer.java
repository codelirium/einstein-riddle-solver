package io.codelirium.constraints.einstein.constraint;

import io.codelirium.constraints.einstein.dto.ModelVariablesDTO;
import io.codelirium.constraints.einstein.entity.EnumEntities.Drink;
import io.codelirium.constraints.einstein.variable.locator.ModelVariablesLocator;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import java.util.List;

import static io.codelirium.constraints.einstein.entity.EnumEntities.Cigarette.*;
import static io.codelirium.constraints.einstein.entity.EnumEntities.Colour.*;
import static io.codelirium.constraints.einstein.entity.EnumEntities.Drink.*;
import static io.codelirium.constraints.einstein.entity.EnumEntities.Nationality.*;
import static io.codelirium.constraints.einstein.entity.EnumEntities.Pet.*;
import static org.springframework.util.Assert.notNull;


@Component
public class HintEnforcer implements Enforcable<Model, ModelVariablesDTO> {


	private ModelVariablesLocator locator;


	@Inject
	public HintEnforcer(final ModelVariablesLocator locator) {

		this.locator = locator;

	}


	private IntVar locate(final List<ModelVariablesDTO> variablesList, final String entity) {

		notNull(variablesList, "TThe list of variables cannot be null.");

		notNull(entity, "The entity cannot be null.");


		return locator.locate(variablesList, entity).orElseThrow(IllegalArgumentException::new);
	}


	@Override
	public void enforce(final Model model, final List<ModelVariablesDTO> variablesList) {

		notNull(model, "The model cannot be null.");

		notNull(variablesList, "The list of variables cannot be null.");



		// 1)  The Brit lives in the red house.

		final IntVar brit = locate(variablesList, BRIT.name());

		final IntVar red  = locate(variablesList, RED.name());


		brit.eq(red).post();



		// 2)  The Swede keeps dogs as pets.

		final IntVar swede = locate(variablesList, SWEDE.name());

		final IntVar dog   = locate(variablesList, DOG.name());


		swede.eq(dog).post();



		// 3)  The Dane drinks tea.

		final IntVar dane = locate(variablesList, DANE.name());

		final IntVar tea  = locate(variablesList, TEA.name());


		dane.eq(tea).post();



		// 4)  The green house is on the left of the white house.

		final IntVar green = locate(variablesList, GREEN.name());

		final IntVar white = locate(variablesList, WHITE.name());


		white.sub(green).eq(1).post();



		// 5)  The green house's owner drinks coffee.

		final IntVar coffee = locate(variablesList, COFFEE.name());


		green.eq(coffee).post();



		// 6)  The person who smokes Pall Mall rears birds.

		final IntVar pallMall = locate(variablesList, PALL_MALL.name());

		final IntVar bird     = locate(variablesList, BIRD.name());


		pallMall.eq(bird).post();



		// 7)  The owner of the yellow house smokes Dunhill.

		final IntVar yellow  = locate(variablesList, YELLOW.name());

		final IntVar dunhill = locate(variablesList, DUNHILL.name());


		yellow.eq(dunhill).post();



		// 8)  The man living in the center house drinks milk.

		final IntVar milk = locate(variablesList, MILK.name());


		milk.eq(Drink.values().length / 2).post();



		// 9)  The Norwegian lives in the first house.

		final IntVar norwegian = locate(variablesList, NORWEGIAN.name());


		norwegian.eq(0).post();



		// 10) The man who smokes Blends lives next to the one who keeps cats.

		final IntVar blends = locate(variablesList, BLENDS.name());

		final IntVar cat    = locate(variablesList, CAT.name());


		blends.sub(cat).abs().eq(1).post();



		// 11) The man who keeps horses lives next to the man who smokes Dunhill.

		final IntVar horse = locate(variablesList, HORSE.name());


		horse.sub(dunhill).abs().eq(1).post();



		// 12) The owner who smokes BlueMaster drinks beer.

		final IntVar blueMaster = locate(variablesList, BLUEMASTER.name());

		final IntVar beer       = locate(variablesList, BEER.name());


		blueMaster.eq(beer).post();



		// 13) The German smokes Prince.

		final IntVar german = locate(variablesList, GERMAN.name());

		final IntVar prince = locate(variablesList, PRINCE.name());


		german.eq(prince).post();



		// 14) The Norwegian lives next to the blue house.

		final IntVar blue = locate(variablesList, BLUE.name());


		norwegian.sub(blue).abs().eq(1).post();



		// 15) The man who smokes blends has a neighbor who drinks water.

		final IntVar water = locate(variablesList, WATER.name());


		blends.sub(water).abs().eq(1).post();
	}
}
