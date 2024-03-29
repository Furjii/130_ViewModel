package com.example.praktikum_5

import android.content.pm.ChangedPackages
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikum_5.data.DataForm
import com.example.praktikum_5.data.DataSource.jenis
import com.example.praktikum_5.data.DataSourcee.status
import com.example.praktikum_5.ui.theme.CobaViewModel
import com.example.praktikum_5.ui.theme.Praktikum_5Theme
import java.util.Optional

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Praktikum_5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    TampilLayout()
                    Register()
                }
            }
        }
    }
}

@Composable
fun Register(modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
        Spacer(modifier = Modifier.padding(3.dp))

        Text(text = "Register", fontSize = 40.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SelectJK(
    options: List<String>, oneSelectionChanged: (String) -> Unit = {}
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Row(modifier = Modifier.padding(16.dp)) {
        options.forEach { item ->
            Row(modifier = Modifier.selectable(selected = selectedValue == item, onClick = {
                selectedValue = item
                oneSelectionChanged(item)
            }), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = selectedValue == item, onClick = {
                    selectedValue = item
                    oneSelectionChanged(item)
                })
                Text(item)
            }

        }

    }

}

@Composable
fun SelectST(
    options: List<String>, oneSelectionChanged: (String) -> Unit = {}
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Row(modifier = Modifier.padding(16.dp)) {
        options.forEach { item ->
            Row(modifier = Modifier.selectable(selected = selectedValue == item, onClick = {
                selectedValue = item
                oneSelectionChanged(item)
            }), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = selectedValue == item, onClick = {
                    selectedValue = item
                    oneSelectionChanged(item)
                })
                Text(item)
            }

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilForm(cobaViewModel: CobaViewModel = viewModel()) {

    var textNama by remember { mutableStateOf("") }
    var textTlp by remember { mutableStateOf("") }
    var textAmt by remember { mutableStateOf("") }
    var textEma by remember { mutableStateOf("") }


    val context = LocalContext.current
    val dataForm: DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataForm = uiState



    OutlinedTextField(value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "User Name") },
        onValueChange = {
            textNama = it
        })

    OutlinedTextField(value = textTlp,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Telepon") },
        onValueChange = {
            textTlp = it
        })
    OutlinedTextField(value = textEma,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email") },
        onValueChange = {
            textEma = it
        })

    SelectJK(options = jenis.map { id -> context.resources.getString(id) },
        oneSelectionChanged = { cobaViewModel.setJenisK(it) })


    OutlinedTextField(value = textAmt,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Alamat Lengkap") },
        onValueChange = {
            textAmt = it
        })

    Button(modifier = Modifier.fillMaxWidth(), onClick = {
        cobaViewModel.insertData(textNama, textTlp, textAmt, textEma, dataForm.sex)
    }) {
        Text(
            text = stringResource(R.string.register), fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(100.dp))
    TextHasil(
        namanya = cobaViewModel.namaUsr,
        telponnya = cobaViewModel.noTelp,
        alamatnya = cobaViewModel.almatUsr,
        emailnya = cobaViewModel.emailUsr,
        jenisnya = cobaViewModel.jenisKl
}

@Composable
fun TampilLayout(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            TampilForm()
        }
    }
}

@Composable
fun TextHasil(
    namanya: String,
    telponnya: String,
    alamatnya: String,
    emailnya: String,
    jenisnya: String,
    status: String,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ), modifier = Modifier.fillMaxWidth()

    ) {
        Text(
            text = "Nama :" + namanya,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
        Text(
            text = "Telepon :" + telponnya,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
        )
        Text(
            text = "Email :" + emailnya,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
        )
        Text(
            text = "Alamat :" + alamatnya,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
        )
        Text(
            text = "Jenis kelamin : " + jenisnya,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
        )

    }
}

@Composable
fun Greeting() {
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Praktikum_5Theme {
        TampilLayout()
        Register()
    }
}