package com.example.lab4v3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab4v3.ui.theme.Lab4v3Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
/*
import coil.compose.rememberImagePainter
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import coil.compose.rememberImagePainter
import androidx.compose.material3.TextField


*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4v3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeListScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge
    )
}

/*@SuppressLint("MutableCollectionMutableState")*/
/**@OptIn(ExperimentalMaterial3Api::class)*/
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoilApi
@Composable
fun RecipeListScreen() {
    val itemList by remember { mutableStateOf(mutableStateListOf<Recipe>()) }
    var recipeName by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input fields for recipe name and image URL
        TextField(
            value = recipeName,
            onValueChange = { recipeName = it },
            label = { Text("Nombre de Receta") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        TextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        // Add button
        Button(
            onClick = {
                if (recipeName.isNotBlank() && imageUrl.isNotBlank()) {
                    itemList.add(Recipe(recipeName, imageUrl))
                    recipeName = ""
                    imageUrl = ""
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(text = "Agregar")
        }

        // Display list of recipes
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(itemList) { recipe ->
                RecipeItem(recipe)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun RecipeItem(recipe: Recipe) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = recipe.imageUrl).apply {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }.build()
            ),
            contentDescription = null,
            modifier = Modifier.size(50.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

data class Recipe(val name: String, val imageUrl: String)

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4v3Theme {
        RecipeListScreen()
    }
}