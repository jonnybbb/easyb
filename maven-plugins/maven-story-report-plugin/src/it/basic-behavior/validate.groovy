storyReport = new File("${basedir}/target/easyb-stories")
assert storyReport.exists()

assert new File(storyReport, "EmptyStackStory.html").exists()
assert new File(storyReport, "ZipCodeStory.html").exists()