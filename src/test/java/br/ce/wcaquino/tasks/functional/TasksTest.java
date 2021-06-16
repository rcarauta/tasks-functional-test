package br.ce.wcaquino.tasks.functional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TasksTest {
	
	public WebDriver acessarAplicacao() {
		System.setProperty("webdriver.chrome.driver", "/home/renato/Downloads/desenvolvimento/cursos/jenkins/chromedriver_linux64/chromedriver");
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() {
		WebDriver driver = acessarAplicacao();
		
		try {		
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		}finally {
			driver.quit();
		}		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		WebDriver driver = acessarAplicacao();
		
		try {		
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
		}finally {
			driver.quit();
		}		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		WebDriver driver = acessarAplicacao();
		
		try {		
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
		}finally {
			driver.quit();
		}		
	}
	
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		WebDriver driver = acessarAplicacao();
		
		try {		
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		}finally {
			driver.quit();
		}		
	}

}
