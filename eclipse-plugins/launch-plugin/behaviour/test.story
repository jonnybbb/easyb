
	int test
scenario "This is a test",{
	
	given "a test",{
		test = new Integer(1)
	}
	
	when "2 is added",{
		test += 2
	}
	
	then "it should equal 3",{
		test.shouldBe 3
	}
}

