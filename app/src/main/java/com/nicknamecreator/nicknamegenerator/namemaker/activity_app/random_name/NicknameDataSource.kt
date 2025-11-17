package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.random_name

import com.nicknamecreator.nicknamegenerator.namemaker.data.model.RandomNicknameModel

object NicknameDataSource {
    
    private val topNicknames = listOf(
        "Shadow", "Phoenix", "Dragon", "Thunder", "Storm", "Blaze", "Frost", "Viper", "Titan", "Raven",
        "Ghost", "Ninja", "Warrior", "Legend", "Champion", "Master", "King", "Queen", "Prince", "Princess",
        "Knight", "Samurai", "Spartan", "Viking", "Gladiator", "Hunter", "Sniper", "Assassin", "Reaper", "Demon",
        "Angel", "Saint", "Hero", "Villain", "Joker", "Ace", "Duke", "Baron", "Lord", "Lady",
        "Captain", "Commander", "General", "Admiral", "Marshal", "Chief", "Boss", "Alpha", "Omega", "Prime",
        "Neo", "Cyber", "Tech", "Digital", "Virtual", "Matrix", "Pixel", "Byte", "Code", "Hack",
        "Pro", "Elite", "Supreme", "Ultimate", "Mega", "Super", "Hyper", "Ultra", "Max", "Apex",
        "Star", "Nova", "Cosmic", "Galaxy", "Solar", "Lunar", "Eclipse", "Comet", "Meteor", "Nebula",
        "Mystic", "Magic", "Wizard", "Sorcerer", "Enchanter", "Warlock", "Witch", "Mage", "Sage", "Oracle",
        "Phantom", "Specter", "Wraith", "Spirit", "Soul", "Shade", "Banshee", "Ghoul", "Zombie", "Vampire"
    )
    
    private val girlNicknames = listOf(
        "Sophia", "Emma", "Olivia", "Ava", "Isabella", "Mia", "Charlotte", "Amelia", "Harper", "Evelyn",
        "Abigail", "Emily", "Elizabeth", "Sofia", "Avery", "Ella", "Scarlett", "Grace", "Chloe", "Victoria",
        "Riley", "Aria", "Lily", "Aubrey", "Zoey", "Penelope", "Lillian", "Addison", "Layla", "Natalie",
        "Camila", "Hannah", "Brooklyn", "Zoe", "Nora", "Leah", "Savannah", "Audrey", "Claire", "Eleanor",
        "Skylar", "Ellie", "Samantha", "Stella", "Paisley", "Violet", "Mila", "Allison", "Alexa", "Anna",
        "Hazel", "Aaliyah", "Ariana", "Lucy", "Caroline", "Sarah", "Genesis", "Kennedy", "Sadie", "Gabriella",
        "Madelyn", "Adeline", "Maya", "Autumn", "Aurora", "Piper", "Hailey", "Eliana", "Naomi", "Alice",
        "Elena", "Madeline", "Serenity", "Luna", "Willow", "Gianna", "Ivy", "Bella", "Ruby", "Nova",
        "Clara", "Emilia", "Quinn", "Isla", "Eva", "Delilah", "Josephine", "Kinsley", "Valentina", "Athena",
        "Melody", "Jade", "Lydia", "Brielle", "Vivian", "Rylee", "Peyton", "Julia", "Reagan", "Natalia"
    )
    
    private val animalNicknames = listOf(
        "Tiger", "Lion", "Bear", "Wolf", "Eagle", "Hawk", "Falcon", "Panther", "Leopard", "Cheetah",
        "Jaguar", "Puma", "Lynx", "Fox", "Coyote", "Jackal", "Hyena", "Badger", "Otter", "Beaver",
        "Rabbit", "Hare", "Squirrel", "Chipmunk", "Raccoon", "Skunk", "Porcupine", "Hedgehog", "Mole", "Shrew",
        "Bat", "Owl", "Raven", "Crow", "Magpie", "Jay", "Robin", "Sparrow", "Finch", "Canary",
        "Parrot", "Macaw", "Cockatoo", "Toucan", "Pelican", "Flamingo", "Heron", "Crane", "Stork", "Ibis",
        "Duck", "Goose", "Swan", "Penguin", "Albatross", "Seagull", "Puffin", "Cormorant", "Kingfisher", "Woodpecker",
        "Shark", "Dolphin", "Whale", "Orca", "Seal", "Walrus", "Manatee", "Octopus", "Squid", "Jellyfish",
        "Turtle", "Tortoise", "Crocodile", "Alligator", "Lizard", "Gecko", "Iguana", "Chameleon", "Snake", "Python",
        "Cobra", "Viper", "Rattlesnake", "Anaconda", "Boa", "Frog", "Toad", "Salamander", "Newt", "Axolotl",
        "Butterfly", "Dragonfly", "Bee", "Wasp", "Hornet", "Ant", "Beetle", "Ladybug", "Firefly", "Mantis"
    )
    
    private val lovedNicknames = listOf(
        "Sweetheart", "Darling", "Honey", "Baby", "Angel", "Sunshine", "Moonlight", "Starlight", "Precious", "Treasure",
        "Beloved", "Dearest", "Cutie", "Sweetie", "Sugar", "Candy", "Cookie", "Cupcake", "Muffin", "Pumpkin",
        "Peach", "Cherry", "Berry", "Apple", "Plum", "Lemon", "Lime", "Orange", "Grape", "Melon",
        "Rose", "Lily", "Daisy", "Violet", "Jasmine", "Iris", "Tulip", "Orchid", "Lotus", "Blossom",
        "Pearl", "Diamond", "Ruby", "Emerald", "Sapphire", "Jade", "Crystal", "Jewel", "Gem", "Gold",
        "Silver", "Platinum", "Bronze", "Copper", "Amber", "Ivory", "Coral", "Opal", "Topaz", "Garnet",
        "Heart", "Soul", "Spirit", "Dream", "Hope", "Faith", "Joy", "Peace", "Love", "Grace",
        "Charm", "Beauty", "Wonder", "Magic", "Miracle", "Blessing", "Gift", "Prize", "Reward", "Crown",
        "Star", "Moon", "Sun", "Sky", "Cloud", "Rain", "Snow", "Wind", "Fire", "Water",
        "Earth", "Nature", "Garden", "Forest", "Ocean", "River", "Lake", "Mountain", "Valley", "Meadow"
    )
    
    private val coolNicknames = listOf(
        "Maverick", "Rebel", "Rogue", "Outlaw", "Bandit", "Raider", "Marauder", "Pirate", "Corsair", "Buccaneer",
        "Ace", "Blade", "Bolt", "Dash", "Flash", "Jet", "Rocket", "Turbo", "Nitro", "Blitz",
        "Venom", "Viper", "Cobra", "Python", "Mamba", "Rattler", "Striker", "Reaper", "Slayer", "Crusher",
        "Thunder", "Lightning", "Storm", "Tempest", "Hurricane", "Tornado", "Cyclone", "Typhoon", "Blizzard", "Avalanche",
        "Inferno", "Blaze", "Flame", "Ember", "Spark", "Scorch", "Burn", "Ash", "Smoke", "Cinder",
        "Ice", "Frost", "Freeze", "Chill", "Arctic", "Polar", "Glacier", "Tundra", "Winter", "Crystal",
        "Shadow", "Shade", "Dark", "Night", "Midnight", "Eclipse", "Twilight", "Dusk", "Dawn", "Sunrise",
        "Neon", "Laser", "Plasma", "Ion", "Atom", "Quantum", "Photon", "Electron", "Proton", "Neutron",
        "Steel", "Iron", "Titanium", "Chrome", "Metal", "Alloy", "Carbon", "Diamond", "Granite", "Obsidian",
        "Vortex", "Nexus", "Apex", "Vertex", "Zenith", "Nadir", "Axis", "Core", "Prime", "Alpha"
    )
    
    private val cuteNicknames = listOf(
        "Bunny", "Kitty", "Puppy", "Teddy", "Panda", "Koala", "Hamster", "Guinea", "Chinchilla", "Ferret",
        "Duckling", "Chick", "Lamb", "Calf", "Foal", "Fawn", "Cub", "Pup", "Kitten", "Joey",
        "Bubbles", "Giggles", "Dimples", "Twinkle", "Sparkle", "Glitter", "Shimmer", "Glimmer", "Shine", "Glow",
        "Fluffy", "Fuzzy", "Cuddly", "Snuggly", "Cozy", "Comfy", "Soft", "Silky", "Smooth", "Gentle",
        "Tiny", "Mini", "Little", "Small", "Petite", "Dainty", "Delicate", "Fine", "Sweet", "Lovely",
        "Pretty", "Beautiful", "Gorgeous", "Stunning", "Radiant", "Bright", "Shiny", "Glossy", "Polished", "Sleek",
        "Happy", "Jolly", "Merry", "Cheerful", "Joyful", "Gleeful", "Blissful", "Delighted", "Pleased", "Content",
        "Sunny", "Bright", "Light", "Airy", "Breezy", "Fresh", "Clean", "Pure", "Clear", "Crisp",
        "Peachy", "Rosy", "Pinky", "Coral", "Salmon", "Blush", "Rouge", "Crimson", "Scarlet", "Ruby",
        "Buttercup", "Daisy", "Poppy", "Pansy", "Petunia", "Primrose", "Marigold", "Sunflower", "Daffodil", "Carnation"
    )
    
    private val filmNicknames = listOf(
        "Maverick", "Joker", "Batman", "Superman", "Spiderman", "Ironman", "Thor", "Hulk", "Captain", "Widow",
        "Hawkeye", "Falcon", "Vision", "Scarlet", "Panther", "Strange", "Antman", "Wasp", "Starlord", "Gamora",
        "Rocket", "Groot", "Drax", "Mantis", "Nebula", "Thanos", "Loki", "Odin", "Hela", "Valkyrie",
        "Bond", "Bourne", "Hunt", "Ethan", "Jack", "John", "Jason", "James", "Neo", "Trinity",
        "Morpheus", "Agent", "Oracle", "Architect", "Merovingian", "Persephone", "Seraph", "Niobe", "Ghost", "Cypher",
        "Luke", "Leia", "Han", "Chewie", "Yoda", "Obi", "Anakin", "Vader", "Rey", "Finn",
        "Poe", "Kylo", "Snoke", "Hux", "Phasma", "Maz", "Lando", "Padme", "Qui", "Maul",
        "Potter", "Hermione", "Ron", "Dumbledore", "Snape", "Voldemort", "Sirius", "Lupin", "Hagrid", "McGonagall",
        "Frodo", "Sam", "Gandalf", "Aragorn", "Legolas", "Gimli", "Boromir", "Gollum", "Sauron", "Saruman",
        "Katniss", "Peeta", "Gale", "Haymitch", "Effie", "Snow", "Coin", "Finnick", "Johanna", "Beetee"
    )
    
    private val uniqueNicknames = listOf(
        "Zephyr", "Zenith", "Zodiac", "Zion", "Zeus", "Zara", "Zelda", "Zeke", "Zane", "Zora",
        "Xander", "Xavier", "Xena", "Xerxes", "Xyla", "Xylo", "Xion", "Xara", "Xeno", "Xavi",
        "Quasar", "Quest", "Quinn", "Quill", "Quantum", "Quartz", "Quincy", "Quade", "Quiana", "Quillan",
        "Vex", "Vox", "Vega", "Volt", "Vance", "Vale", "Vail", "Vada", "Vala", "Valen",
        "Onyx", "Orion", "Osiris", "Odin", "Omega", "Oracle", "Opus", "Opal", "Orca", "Oslo",
        "Nexus", "Nova", "Nyx", "Nero", "Nash", "Nala", "Niko", "Nyx", "Nori", "Nessa",
        "Lyric", "Lux", "Luna", "Lynx", "Loki", "Lotus", "Lyra", "Levi", "Luca", "Lexi",
        "Kai", "Koda", "Knox", "Kira", "Kael", "Kyra", "Kian", "Kaya", "Kenzo", "Kaia",
        "Jax", "Jett", "Juno", "Jade", "Jazz", "Jace", "Jada", "Jovi", "Jora", "Juno",
        "Indigo", "Iris", "Ivy", "Icon", "Isla", "Ira", "Ines", "Iona", "Ilya", "Iman"
    )
    
    private val boyNicknames = listOf(
        "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander",
        "Mason", "Michael", "Ethan", "Daniel", "Jacob", "Logan", "Jackson", "Levi", "Sebastian", "Mateo",
        "Jack", "Owen", "Theodore", "Aiden", "Samuel", "Joseph", "John", "David", "Wyatt", "Matthew",
        "Luke", "Asher", "Carter", "Julian", "Grayson", "Leo", "Jayden", "Gabriel", "Isaac", "Lincoln",
        "Anthony", "Hudson", "Dylan", "Ezra", "Thomas", "Charles", "Christopher", "Jaxon", "Maverick", "Josiah",
        "Isaiah", "Andrew", "Elias", "Joshua", "Nathan", "Caleb", "Ryan", "Adrian", "Miles", "Eli",
        "Nolan", "Christian", "Aaron", "Cameron", "Ezekiel", "Colton", "Luca", "Landon", "Hunter", "Jonathan",
        "Santiago", "Axel", "Easton", "Cooper", "Jeremiah", "Angel", "Roman", "Connor", "Jameson", "Robert",
        "Greyson", "Jordan", "Ian", "Carson", "Jaxson", "Leonardo", "Nicholas", "Dominic", "Austin", "Everett",
        "Brooks", "Xavier", "Kai", "Jose", "Parker", "Adam", "Jace", "Wesley", "Kayden", "Silas"
    )
    
    private val foodNicknames = listOf(
        "Pepper", "Ginger", "Basil", "Sage", "Thyme", "Rosemary", "Mint", "Parsley", "Cilantro", "Dill",
        "Cinnamon", "Vanilla", "Cocoa", "Mocha", "Latte", "Espresso", "Cappuccino", "Macchiato", "Americano", "Frappe",
        "Cookie", "Brownie", "Muffin", "Cupcake", "Donut", "Croissant", "Bagel", "Waffle", "Pancake", "Crepe",
        "Pizza", "Pasta", "Ravioli", "Lasagna", "Spaghetti", "Fettuccine", "Linguine", "Penne", "Rigatoni", "Tortellini",
        "Sushi", "Ramen", "Udon", "Soba", "Tempura", "Teriyaki", "Wasabi", "Miso", "Tofu", "Edamame",
        "Taco", "Burrito", "Quesadilla", "Nacho", "Salsa", "Guacamole", "Jalapeno", "Chipotle", "Fajita", "Enchilada",
        "Burger", "Hotdog", "Sandwich", "Wrap", "Panini", "Sub", "Hoagie", "Baguette", "Ciabatta", "Focaccia",
        "Steak", "Bacon", "Ham", "Sausage", "Salami", "Pepperoni", "Prosciutto", "Chorizo", "Brisket", "Ribs",
        "Chicken", "Turkey", "Duck", "Goose", "Quail", "Pheasant", "Partridge", "Grouse", "Pigeon", "Dove",
        "Salmon", "Tuna", "Trout", "Bass", "Cod", "Halibut", "Snapper", "Grouper", "Mahi", "Tilapia"
    )
    
    private val musicNicknames = listOf(
        "Melody", "Harmony", "Rhythm", "Beat", "Tempo", "Chord", "Note", "Tune", "Song", "Lyric",
        "Jazz", "Blues", "Rock", "Pop", "Funk", "Soul", "Disco", "Techno", "House", "Trance",
        "Hip", "Hop", "Rap", "Trap", "Drill", "Grime", "Dubstep", "Drum", "Bass", "Jungle",
        "Metal", "Punk", "Grunge", "Emo", "Indie", "Alternative", "Gothic", "Industrial", "Hardcore", "Screamo",
        "Country", "Folk", "Bluegrass", "Americana", "Western", "Honky", "Outlaw", "Nashville", "Austin", "Memphis",
        "Classical", "Opera", "Symphony", "Concerto", "Sonata", "Prelude", "Nocturne", "Etude", "Waltz", "Minuet",
        "Reggae", "Ska", "Dub", "Dancehall", "Calypso", "Soca", "Zouk", "Kompa", "Merengue", "Bachata",
        "Salsa", "Mambo", "Rumba", "Tango", "Flamenco", "Fado", "Bossa", "Samba", "Cumbia", "Mariachi",
        "Guitar", "Piano", "Violin", "Cello", "Viola", "Bass", "Drums", "Trumpet", "Saxophone", "Clarinet",
        "Flute", "Oboe", "Bassoon", "Trombone", "Tuba", "Horn", "Harp", "Banjo", "Mandolin", "Ukulele"
    )
    
    private val gameNicknames = listOf(
        "Gamer", "Player", "Noob", "Pro", "Legend", "Master", "Champion", "Winner", "Loser", "Rookie",
        "Warrior", "Mage", "Rogue", "Ranger", "Paladin", "Cleric", "Druid", "Monk", "Barbarian", "Bard",
        "Tank", "DPS", "Healer", "Support", "Carry", "Jungler", "Mid", "Top", "Bot", "ADC",
        "Sniper", "Scout", "Heavy", "Medic", "Engineer", "Spy", "Pyro", "Demoman", "Soldier", "Assault",
        "Striker", "Defender", "Goalkeeper", "Midfielder", "Forward", "Winger", "Sweeper", "Stopper", "Fullback", "Halfback",
        "Racer", "Driver", "Pilot", "Captain", "Commander", "General", "Admiral", "Marshal", "Colonel", "Major",
        "Knight", "Bishop", "Rook", "Queen", "King", "Pawn", "Checkmate", "Stalemate", "Gambit", "Castle",
        "Ace", "King", "Queen", "Jack", "Joker", "Spade", "Heart", "Diamond", "Club", "Trump",
        "Dice", "Roll", "Bet", "Fold", "Call", "Raise", "Bluff", "Poker", "Blackjack", "Roulette",
        "Mario", "Luigi", "Peach", "Bowser", "Yoshi", "Toad", "Wario", "Waluigi", "Donkey", "Diddy"
    )
    
    fun getNicknamesForCategory(categoryId: String): List<String> {
        return when (categoryId) {
            "top" -> topNicknames
            "girl" -> girlNicknames
            "animals" -> animalNicknames
            "loved" -> lovedNicknames
            "cool" -> coolNicknames
            "cute" -> cuteNicknames
            "film" -> filmNicknames
            "unique" -> uniqueNicknames
            "boy" -> boyNicknames
            "food" -> foodNicknames
            "music" -> musicNicknames
            "game" -> gameNicknames
            else -> topNicknames
        }
    }
    
    fun getRandomNicknames(categoryId: String, count: Int = 20): List<RandomNicknameModel> {
        val allNicknames = getNicknamesForCategory(categoryId)

        return allNicknames.shuffled().take(count).map { nickname ->
            // Use FULL emoji collection from CustomizeNicknameActivity (200+ emojis)
            // Randomly select an emoji for decoration
            val randomEmoji = com.nicknamecreator.nicknamegenerator.namemaker.activity_app.nickname.SymbolConstants.ALL_EMOJIS.random()

            // Use FULL styles collection from CustomizeNicknameActivity (180+ styles)
            // Randomly select a Unicode style from ALL available styles
            val randomStyle = com.nicknamecreator.nicknamegenerator.namemaker.activity_app.nickname.StyleConstants.ALL_STYLES.random()

            // Add emoji decoration to nickname first (like CustomizeNicknameActivity)
            val baseText = "$randomEmoji $nickname $randomEmoji"

            // Then apply the Unicode style to the entire text including emojis
            val finalNickname = com.nicknamecreator.nicknamegenerator.namemaker.activity_app.nickname.CustomizeNicknameActivityStyleApplier.applyUnicodeStyle(baseText, randomStyle)

            // Return with metadata for saving
            RandomNicknameModel(
                nickname = nickname,
                category = categoryId,
                styledNickname = finalNickname,
                styleType = randomStyle,
                leftSymbol = randomEmoji,
                rightSymbol = randomEmoji
            )
        }
    }
}

