import xml.etree.ElementTree as ET
import os
import random
import re
import requests

class Statute:

    path = None
    statuteID = None
    statuteType = None
    documentType = None
    number = None
    year = None
    name = None
    fullName = None
    language = None
    documentReferences = None
    enactingClause = None
    chapters = []
    runningSections = None
    sections = []
    referenceParts = None
    signaturePart = None

    def defineNumber(self):
        tags = documentTags()
        documentNumStrLst = []
        tree = ET.parse(self.path)
        root = tree.getroot()
        for elem in tree.iter(tags.get("number")):
            documentNumStrLst.append(elem.text)
        assert(len(documentNumStrLst)==1)
        self.number = documentNumStrLst[0]

    def defineYear(self):
        tags = documentTags()
        documentYearStrLst = []
        tree = ET.parse(self.path)
        root = tree.getroot()
        for elem in tree.iter(tags.get("year")):
            documentYearStrLst.append(elem.text)
        assert(len(documentYearStrLst)==1)
        self.year = documentYearStrLst[0]

    def defineStatuteID(self):
        assert(self.year != None and self.number != None)
        self.statuteID = self.number + "/" + self.year

    def defineStatuteType(self):
        tags = documentTags()
        tree = ET.parse(self.path)
        root = tree.getroot()
        for elem in tree.iter(tags.get("statuteType")):
            self.statuteType = elem.text
    
    def defineDocumentType(self):
        tags = documentTags()
        tree = ET.parse(self.path)
        root = tree.getroot()
        for elem in tree.iter(tags.get("documentType")):
            self.documentType = elem.text

    def defineStatuteName(self):
        tags = documentTags()
        tree = ET.parse(self.path)
        root = tree.getroot()
        for elem in tree.iter(tags.get("name")):
            self.name = elem.text
    
    def defineStatuteFullName(self):
        if (self.name != None and self.statuteType != None):
            self.fullName = self.statuteType + " " + self.name
        else: 
            self.fullName = "NONAME"

    def defineEnactingClause(self):
        self.enactingClause = EnactingClause()
        tags = documentTags()
        tree = ET.parse(self.path)
        root = tree.getroot()
        for enactingClause in tree.iter(tags.get("enactingClause")):
            for content in enactingClause.iter(tags.get("enactingClauseContent")):
                newEnactingClauseSection = EnactingClauseSection()
                newEnactingClauseSection.text = content.text
                self.enactingClause.enactingClauseSections.append(newEnactingClauseSection)

    def defineChapters(self):
        tags = chapterTags()
        tree = ET.parse(self.path)
        root = tree.getroot()
        for chapter in tree.iter(tags.get("chapter")):
            newChapter = Chapter()
            for identifier in chapter.iter(tags.get("chapterID")):
                newChapter.identifier = identifier.text
            for heading in chapter.iter(tags.get("heading")):
                newChapter.heading = heading.text
            
            self.chapters.append(newChapter)
            
    def defineSections(self):
        #assert (self.runningSections == True)
        tags = sectionTags()
        tree = ET.parse(self.path)
        root = tree.getroot()
        for section in tree.iter(tags.get("section")):
            newSection = Section()
            for sectionID in section.iter(tags.get("identifier")):
                newSection.identifier = sectionID.text
            for sectionHeading in section.iter(tags.get("heading")):
                newSection.heading = sectionHeading.text
            newSection.iterableXml = section
            newSection.subsections = self.defineSubsections(aSection = newSection)
            self.sections.append(newSection)
    
    def defineSubsections(self, aSection):
        secTags = sectionTags()
        subTags = subsectionTags()
        paraTags = paragraphTags()
        sectionXML = aSection.iterableXml
        subsectionsLst = []
        position = 1
        for section in sectionXML.iter(secTags.get("section")):
            for element in section:
                if (element.tag == subTags.get("text")):
                    newSubsection = Subsection()
                    newSubsection.text = element.text
                    newSubsection.position = position
                    subsectionsLst.append(newSubsection)
                    position += 1

                elif (element.tag == paraTags.get("paragraphs")):
                    newSubsection = Subsection()
                    newSubsection.paragraphs = self.defineParagraphs(element)
                    newSubsection.position = position
                    subsectionsLst.append(newSubsection)
                    position += 1

        return subsectionsLst

    def defineParagraphs(self, paraRoot):
        paraTags = paragraphTags()
        paraRoot = paraRoot
        position = 1
        paragraphLst = []
        for element in paraRoot.iter():
            if (element.tag == paraTags.get("paragraphPreamble")):
                newParagraph = Paragraph()
                newParagraph.isPreamble = True
                newParagraph.preamble = element.text
                newParagraph.position = position-1
                paragraphLst.append(newParagraph)

            if (element.tag == paraTags.get("text")):
                newParagraph = Paragraph()
                newParagraph.isPreamble = False
                newParagraph.text = element.text
                newParagraph.position = position
                paragraphLst.append(newParagraph)
                position += 1

        return paragraphLst
            

    def hasParagraphs(self, iterableSection):
        tags = self.getSectionXMLElements(iterableSection)
        for tag in tags:
            if ("MomenttiKohtaKooste" in str(tag)):
                return True
        return False

    #remove?
    def getSectionXMLElements(self, iterableSection):
        elements = []
        for element in iterableSection.iter():
            elements.append(element.tag)
        return elements                         

    def initStatute(self):
        try:
            self.defineNumber()
            self.defineYear()       
            self.defineStatuteID()
            self.defineDocumentType()
            self.defineStatuteType()
            self.defineStatuteName()
            self.defineStatuteFullName()
            self.defineEnactingClause()
            self.defineChapters()
            self.defineSections()
        except:
            self.statuteID = "NO ID"
            

        #TODO
    
#--------------------------------------------STATUTE TESTING------------------------------------------------



    def printSectionTags(self, aSection):
        secTags = sectionTags()
        subTags = subsectionTags()
        paraTags = paragraphTags()
        sectionXML = aSection.iterableXml
        print("-----------------------------------------------------------------")
        print("ID: " + self.statuteID)
        for section in sectionXML.iter(secTags.get("section")):
            for element in section:
                print(element.tag)
        print("-----------------------------------------------------------------")


    def printSectionXMLTags(self):
        secTags = sectionTags()
        subTags = subsectionTags()
        tree = ET.parse(self.path)
        root = tree.getroot()
        print("ID: " + self.statuteID) 
        for section in tree.iter(secTags.get("section")):
            for iterObj in section.iter():
                print(iterObj)

    def printStatute(self):
        print("\nID: " + self.statuteID + "\n")
        if (self.statuteType != None):
            print("Statute type: " + self.statuteType)
        print("Document type: " + self.documentType)
        print("Name: " + self.name)
        print("Säädöksen koko nimi: " + self.fullName)
        if (self.enactingClause != None):
            print("\nJohtolause:\n")
            for eSection in self.enactingClause.enactingClauseSections:
                print(eSection.text + "\n")
        for section in self.sections:
            if (section.heading != None):
                if (section.identifier == None):
                    print("\n\nEI PYKÄLÄTUNNUSTA: " + section.text)
                elif (section.identifier != None):
                    print("\n\n" + section.identifier + " " + section.heading + ": \n" )
            elif (section.heading == None and section.identifier != None):
                print(section.identifier + ": \n")
            elif (section.identifier == None):
                print("EI PYKÄLÄTUNNUSTA EIKÄ OTSIKKOA \n")

            for subsection in section.subsections:
                if (subsection.position != None and subsection.text != None):
                    print(str(subsection.position) + ". mom: " + subsection.text + "\n")
                elif(subsection.position != None and subsection.text == None and len(subsection.paragraphs)>0):
                    for paragraph in subsection.paragraphs:
                        if (paragraph.isPreamble == True):
                            print("Kohdan johtolause: " + paragraph.preamble + "\n")
                        elif (paragraph.isPreamble == False):
                            print("Pos. " + str(paragraph.position) + paragraph.text +"\n")
                        else:
                             (print("\n\nWHAAAAAAAAAAAAAAAAAAT\n\n"))

    def printSectionHeadings(self):
        print(self.statuteID)
        for section in self.sections:
            if (section.heading != None):
                print(section.heading + "\n")
            elif (section.heading == None):
                print("Pykälällä ei otsikkoa")

    def printParagraphs(self):
        for section in self.sections:
            for subsection in section.subsections:
                if (subsection.paragraphs != None):
                    print("addsfdsfsadf" + str(subsection.position))
                    for paragraph in subsection.paragraphs:
                        assert(paragraph.isPreamble != None)
                        if (paragraph.isPreamble == True):
                            print("Momentin (nro: " + str(paragraph.position) + ") esipuhe: " + paragraph.text + "\n")
                        elif (paragraph.isPreamble == False):
                            print(str(paragraph.position) + ". kohta: " + paragraph.text + "\n")


#-------------------------------------------------------------------------------------------------------------------------------------

class Chapter:

    identifier = None
    number = None
    heading = None 
    sections = None

    def defineNumber(self):
        assert(identifier != None)


class Section:
    
    def __init__(self):
        pass

    sectionID = None
    identifier = None
    classification = None
    heading = None
    subsections = []
    #Iterable element tree object rooted in the current section. 
    iterableXml = None

    #--------------TESTING------------------

    def getTreeElements(self):
        elements = []
        for element in self.iterableXml.iter():
            elements.append(element.tag)
        return (elements)


class Subsection:

    def __init__(self):
        pass

    position = None
    paragraphs = []
    text = None
    iterableXml = None

class Paragraph:

    def __init__(self):
        pass

    isPreamble = None
    preamble = None
    position = None 
    text = None

class EnactingClause:

    enactingClauseSections = []

class EnactingClauseSection:

    text = None

class SignaturePart:

    date = None
    signatories = None 

class Signator:

    rank = None
    name = None 

###############
class Picture:

    pictureID = None
    text = None



def documentTags():
    
    documentType = "{http://www.vn.fi/skeemat/metatietoelementit/2010/04/27}AsiakirjatyyppiNimi"
    #tyyppikoodi = "{http://www.vn.fi/skeemat/metatietoelementit/2010/04/27}AsiakirjatyyppiKoodi"
    number = "{http://www.vn.fi/skeemat/asiakirjaelementit/2010/04/27}AsiakirjaNroTeksti"
    year = "{http://www.vn.fi/skeemat/asiakirjaelementit/2010/04/27}ValtiopaivavuosiTeksti"
    #name = "{http://www.vn.fi/skeemat/metatietoelementit/2010/04/27}NimekeTeksti"
    #viiteteksti = "{http://www.vn.fi/skeemat/sisaltoelementit/2010/04/27}ViiteTeksti"
    #---------------------------------------------------------------------------------------------
    enactingClause = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}Johtolause" #--->
    enactingClauseContent = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}SaadosKappaleKooste"
    #---------------------------------------------------------------------------------------------
    statuteType = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}SaadostyyppiKooste"
    name = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}SaadosNimekeKooste"

    tagDic = {"documentType":documentType, "number":number, "year":year, "statuteType":statuteType, "name":name, "enactingClause":enactingClause, 
    "enactingClauseContent":enactingClauseContent}

    return tagDic

def chapterTags():

    chapterID = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}LukuTunnusKooste"
    chapter = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}Luku" #???
    heading = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}SaadosOtsikkoKooste"

    tagDic = {"chapterID":chapterID, "chapter":chapter, "heading":heading}
    return tagDic

def sectionTags():

    section = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}Pykala" #Huom. ei tekstisisältöä
    identifier = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}PykalaTunnusKooste"
    heading = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}SaadosOtsikkoKooste"

    tagDic = {"section":section, "identifier":identifier, "heading":heading}
    return tagDic

def subsectionTags():

    text = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}MomenttiKooste"

    tagDic = {"text":text}
    return tagDic

def paragraphTags():
    paragraphs = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}KohdatMomentti"#??--->

    paragraphPreamble = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}MomenttiJohdantoKooste"
    text = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}MomenttiKohtaKooste"

    tagDic ={"paragraphPreamble":paragraphPreamble, "text":text, "paragraphs":paragraphs}
    return tagDic 

def signaturePartTags():

    date = "{http://www.vn.fi/skeemat/asiakirjakooste/2010/04/27}PaivaysKooste"
    #----
    signator = "{http://www.vn.fi/skeemat/asiakirjakooste/2010/04/27}Allekirjoittaja" #Huom. ei tekstisisältöä
    #--->
    person = "{http://www.vn.fi/skeemat/organisaatiokooste/2010/02/15}Henkilo" #Huom. ei teksisisältöä

    tagDic = {"date":date,"signator":signator,"person":person}
    return tagDic

def signatorTags():
    rank = "{http://www.vn.fi/skeemat/organisaatioelementit/2010/02/15}AsemaTeksti"
    name = "{http://www.vn.fi/skeemat/organisaatioelementit/2010/02/15}SukuNimi"

    tagDic = {"rank":rank, "name":name}
    return tagDic

    #-----------------------------------------------------------------------------------------
    #kappaleKooste = "{http://www.vn.fi/skeemat/saadoskooste/2010/04/27}SaadosKappaleKooste"
    #-------------------------------viivat-------------------------------------------
    #katkoviiva = "{http://www.vn.fi/skeemat/sisaltokooste/2010/04/27}Katkoviiva"
    #palstaviiva = "{http://www.vn.fi/skeemat/sisaltokooste/2010/04/27}Palstaviiva"
    #lihava = "{http://www.vn.fi/skeemat/sisaltoelementit/2010/04/27}LihavaTeksti"
    #{http://www.vn.fi/skeemat/taulukkokooste/2010/04/27}table
    #{http://www.vn.fi/skeemat/taulukkokooste/2010/04/27}tgroup
    #{http://www.vn.fi/skeemat/taulukkokooste/2010/04/27}colspec
    #{http://www.vn.fi/skeemat/taulukkokooste/2010/04/27}colspec
    #{http://www.vn.fi/skeemat/taulukkokooste/2010/04/27}tbody
    #{http://www.vn.fi/skeemat/taulukkokooste/2010/04/27}row
    #{http://www.vn.fi/skeemat/taulukkokooste/2010/04/27}entry

#Lists all files in cwd and in subfolders

def filesToIgnore():
    filenames = ["pyxml.py", "pyxml2.py", "sem.xml", "wtf.txt", "elementit.txt", "ui.py", "__pycache__"]
    return filenames

def allFilePaths():
    filePaths = []
    for root, dirs, files in os.walk(".", topdown=False):
        for name in files:
            if (name in filesToIgnore()):
                continue
            else:
                filePaths.append(os.path.join(root, name))
    return filePaths

def getFolderNames():
    filenames= os.listdir (".") # get all files' and folders' names in the current directory
    result = []
    for filename in filenames: # loop through all the files and folders
        if os.path.isdir(os.path.join(os.path.abspath("."), filename)): # check whether the current object is a folder or not
            if (filename not in filesToIgnore()):
                result.append(filename)
            else:
                continue
    result.sort()
    return result
        
def randomFilePath():
    filePaths = []
    for root, dirs, files in os.walk(".", topdown=False):
        for name in files:
            if (name in filesToIgnore()):
                continue
            else:
                filePaths.append(os.path.join(root, name))
    randomFile = filePaths[random.randint(0, len(filePaths)-1)]
    return randomFile

def createStatute(path):
    newStatute = Statute()
    newStatute.path = path

    return newStatute

def initAllStatutes():
    statutes = {}
    filepaths = allFilePaths()
    for path in filepaths:
        print(path)
        newStatute = createStatute(path)
        newStatute.initStatute()
        statutes[newStatute] = newStatute.statuteID
        print("Added " + newStatute.statuteID)
    return statutes

def showAllStatutes(statutesDic):
    for statute in statutesDic:
        print("Säädöksen ID: " + statute.statuteID)
    print("Säädöksiä " + str(len(statutesDic)) + " kpl")

def getRandomStatute():
    path = randomFilePath()
    statute = createStatute(path)
    statute.initStatute()
    return statute


DOMAIN = "http://localhost:8080"
def sendStatPostRequest(statute):
    endpoint = "/api/lisaa/saados"
    url = DOMAIN+endpoint
    params = {"shortname":statute.name,"num":statute.number, "year":statute.year, "statutetype":statute.statuteType, "language":"Suomi"}
    requests.post(url, data = params)

def sendSecPostRequest(stat, sec):
    endpoint = "/api/lisaa/pykala"
    url = DOMAIN+endpoint
    params = {"statid":stat.statuteID,"identifier":sec.identifier,"heading":sec.heading}
    requests.post(url, data = params)

def sendSubsecPostRequest(stat, sec, subsec):
    endpoint = "/api/lisaa/momentti"
    url = DOMAIN+endpoint
    params = {"statid":stat.statuteID, "secidentifier":sec.identifier, "text":subsec.text}
    requests.post(url, data = params)

def sendParaPostRequest(stat, sec, subsec, para):
    subsEndpoint = "/api/lisaa/momentti" 
    paraEndpoint = "/api/lisaa/kohta"

    subsecUrl = DOMAIN+subsEndpoint
    paraUrl = DOMAIN+paraEndpoint

    if (para.position == 0):
        
        #params = {"statid":stat.statuteID, "secid":sec.identifier, "subspos":subsec.position, "parapos":str(para.position), "text":"hellooooooo "}
        #print(params)
        subsecParams = {"statid":stat.statuteID, "secidentifier":sec.identifier, "text": " "}
        paraParams = {"statid":stat.statuteID, "secid":sec.identifier, "subspos":subsec.position, "parapos":str(para.position), "text":para.preamble, "ispreamble":para.isPreamble}
        print(paraParams)
        requests.post(subsecUrl, data = subsecParams)
        requests.post(paraUrl, data = paraParams)
    elif (para.position > 0):
        params = {"statid":stat.statuteID, "secid":sec.identifier, "subspos":subsec.position, "parapos":str(para.position), "text":para.text, "ispreamble":para.isPreamble}
        print(params)
        requests.post(paraUrl, data = params)
    #else:
        #params = {"statid":stat.statuteID, "secid":sec.identifier, "subspos":subsec.position, "parapos":str(para.position), "text":para.text}
        #print(params)
    #requests.post(url, data = params)

def sendEmptySubsecPostRequest(stat, sec):
    endpoint = "/api/lisaa/kohta" 

    url = DOMAIN+endpoint 
    params = {"statid":stat.statuteID, "secidentifier":sec.identifier, "text":""}
    requests.post(url, data = params)

"""statuteDic = initAllStatutes()
print(statuteDic)
showAllStatutes(statuteDic)"""

count = 0
while(count < 1):

    stat = getRandomStatute()
    sendStatPostRequest(stat)
    print("----------------------------------")
    print(stat.statuteID)
    for sec in stat.sections:
        sendSecPostRequest(stat, sec)
        print(sec.heading)
        for subsec in sec.subsections:
            if (len(subsec.paragraphs) == 0):
                sendSubsecPostRequest(stat, sec, subsec)
                print(subsec.text)
            else:
                sendEmptySubsecPostRequest(stat, sec)
                for para in subsec.paragraphs:
                    sendParaPostRequest(stat, sec, subsec, para)
                    print(para.text)
    count+=1

