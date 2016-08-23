package org.vaadin.styletoggler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addonhelpers.AbstractTest;

import com.github.javafaker.Faker;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("test-theme")
public class MyUI extends AbstractTest {

	static List<Product> prods;
	private CssLayout rootLayout;

	static {
		Faker f = new Faker();
		prods = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			Product p = new MyUI.Product(new BigInteger(i + ""));
			p.code = f.code().isbn10();
			p.name = f.commerce().productName();
			p.lotSize = f.number().numberBetween(1, 25);
			p.lotUnit = f.options().option("pcs", "boxes", "truckloads");
			p.weight = new BigDecimal("" + f.number().numberBetween(80, 1200));
			p.weightUnit = f.options().option("grams", "kg", "tons");
			p.width = new BigDecimal("" + f.number().numberBetween(1, 200));
			p.height = new BigDecimal("" + f.number().numberBetween(1, 200));
			p.depth = new BigDecimal("" + f.number().numberBetween(1, 200));
			p.dimensionsUnit = f.options().option("mm", "cm", "meters");
			p.volume = new BigDecimal("" + f.number().numberBetween(1, 200));
			p.volumeUnit = f.options().option("dl", "liter", "m³");
			p.created = f.date().past(1000, TimeUnit.DAYS);
			p.update = f.date().past(500, TimeUnit.DAYS);
			prods.add(p);
		}
	}

	private Grid createGrid() {
		Grid grid = new Grid();
		grid.setSizeFull();
		grid.setHeightUndefined();
		BeanItemContainer container = new BeanItemContainer<>(Product.class);

		populateContainer(container);

		grid.setContainerDataSource(container);
		grid.setColumnOrder("id", "code", "name", "lotSize", "lotUnit", "weight", "weightUnit", "width", "height",
				"depth", "dimensionsUnit", "volume", "volumeUnit", "update", "created", "strProp1", "strProp2",
				"strProp3", "strProp4", "strProp5", "strProp6", "strProp7", "strProp8");
		grid.setEditorEnabled(true);

		return grid;
	}

	private void populateContainer(BeanItemContainer container) {
		long startTime = System.currentTimeMillis();
		container.addAll(getDummyData());
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("time it took to populate the container: " + elapsedTime);
	}

	private List<Product> getDummyData() {
		return prods;
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

	@Override
	public Component getTestComponent() {
		rootLayout = new CssLayout();
		long startTime = System.currentTimeMillis();
		CssLayout navigation = new CssLayout();
		navigation.addStyleName("nav");
		rootLayout.addComponent(navigation);
		navigation.setHeight("100%");
		Button enhancedToggler = new Button("E");
		new StyleToggler(enhancedToggler, rootLayout, "nav-expanded");
		navigation.addComponent(enhancedToggler);
		enhancedToggler.setDescription("This button toggles the style already on the client-side");
		
		Button normalButton = new Button("T", e -> {
			if (rootLayout.getStyleName().contains("nav-expanded")) {
				rootLayout.removeStyleName("nav-expanded");
			} else {
				rootLayout.addStyleName("nav-expanded");
			}
		});
		normalButton.setDescription("This button toggles the style on the server-side");
		navigation.addComponent(normalButton);

		Grid grid = createGrid();
		grid.setSizeFull();
		CssLayout gridWrapper = new CssLayout(grid);
		rootLayout.addComponent(gridWrapper);
		gridWrapper.addStyleName("grid-wrapper");
		gridWrapper.setHeight("100%");

		rootLayout.setSizeFull();
		rootLayout.addStyleName("root-layout");

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("time it took to create the UI: " + elapsedTime);
		
		return rootLayout;
	}
	public static class Product {
		BigInteger id;
		String code;
		String name;

		int lotSize;
		String lotUnit;

		BigDecimal weight;
		String weightUnit;
		BigDecimal width;
		BigDecimal height;
		BigDecimal depth;
		String dimensionsUnit;
		BigDecimal volume;
		String volumeUnit;

		Date update;
		Date created;
		String strProp1;
		String strProp2;
		String strProp3;
		String strProp4;
		String strProp5;
		String strProp6;
		String strProp7;
		String strProp8;
		
		public Product(BigInteger id) {
			this.id = id;
		}

		public BigInteger getId() {
			return id;
		}
		public void setId(BigInteger id) {
			this.id = id;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getLotSize() {
			return lotSize;
		}
		public void setLotSize(int lotSize) {
			this.lotSize = lotSize;
		}
		public String getLotUnit() {
			return lotUnit;
		}
		public void setLotUnit(String lotUnit) {
			this.lotUnit = lotUnit;
		}
		public BigDecimal getWeight() {
			return weight;
		}
		public void setWeight(BigDecimal weight) {
			this.weight = weight;
		}
		public String getWeightUnit() {
			return weightUnit;
		}
		public void setWeightUnit(String weightUnit) {
			this.weightUnit = weightUnit;
		}
		public BigDecimal getWidth() {
			return width;
		}
		public void setWidth(BigDecimal width) {
			this.width = width;
		}
		public BigDecimal getHeight() {
			return height;
		}
		public void setHeight(BigDecimal height) {
			this.height = height;
		}
		public BigDecimal getDepth() {
			return depth;
		}
		public void setDepth(BigDecimal depth) {
			this.depth = depth;
		}
		public String getDimensionsUnit() {
			return dimensionsUnit;
		}
		public void setDimensionsUnit(String dimensionsUnit) {
			this.dimensionsUnit = dimensionsUnit;
		}
		public BigDecimal getVolume() {
			return volume;
		}
		public void setVolume(BigDecimal volume) {
			this.volume = volume;
		}
		public String getVolumeUnit() {
			return volumeUnit;
		}
		public void setVolumeUnit(String volumeUnit) {
			this.volumeUnit = volumeUnit;
		}
		public Date getUpdate() {
			return update;
		}
		public void setUpdate(Date update) {
			this.update = update;
		}
		public Date getCreated() {
			return created;
		}
		public void setCreated(Date created) {
			this.created = created;
		}
		public String getStrProp1() {
			return strProp1;
		}
		public void setStrProp1(String strProp1) {
			this.strProp1 = strProp1;
		}
		public String getStrProp2() {
			return strProp2;
		}
		public void setStrProp2(String strProp2) {
			this.strProp2 = strProp2;
		}
		public String getStrProp3() {
			return strProp3;
		}
		public void setStrProp3(String strProp3) {
			this.strProp3 = strProp3;
		}
		public String getStrProp4() {
			return strProp4;
		}
		public void setStrProp4(String strProp4) {
			this.strProp4 = strProp4;
		}
		public String getStrProp5() {
			return strProp5;
		}
		public void setStrProp5(String strProp5) {
			this.strProp5 = strProp5;
		}
		public String getStrProp6() {
			return strProp6;
		}
		public void setStrProp6(String strProp6) {
			this.strProp6 = strProp6;
		}
		public String getStrProp7() {
			return strProp7;
		}
		public void setStrProp7(String strProp7) {
			this.strProp7 = strProp7;
		}
		public String getStrProp8() {
			return strProp8;
		}
		public void setStrProp8(String strProp8) {
			this.strProp8 = strProp8;
		}
	}

}
