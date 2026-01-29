## 📱 Expense Tracker App (Android – Kotlin)

Bu uygulama, kullanıcıların **gelir ve giderlerini takip edebilmesi** için geliştirilmiş bir Android uygulamasıdır.
Proje, **Android ve Kotlin öğrenme sürecimde** temel kavramları pekiştirmek amacıyla geliştirilmiştir.

---

## 🚀 Özellikler

* ➕ Gelir ve gider ekleme
* 📂 Kategori seçimi
* 📊 **Kategori bazlı toplam harcama/gider gösterimi**
* 🧮 **Toplam bakiye hesaplama**
* 🔍 **Gelir / Gider / Tümü filtreleme**
* 💾 Verilerin **Room (SQLite)** ile yerel olarak saklanması
* 🔄 **MVVM mimarisi** kullanımı
* 🎨 **Material Design 3** bileşenleri

---

## 🧱 Kullanılan Teknolojiler

* Kotlin
* Android Studio
* Room Database (SQLite)
* MVVM Architecture
* ViewModel & LiveData
* Kotlin Coroutines
* RecyclerView
* Material Design 3

---

## 🧠 Mimari Yapı (MVVM)

Bu projede **MVVM (Model – View – ViewModel)** mimarisi kullanılmıştır.

### **Model**

* Room **Entity**, **DAO** ve **Repository**
* Veritabanı işlemlerinden ve veri erişiminden sorumludur

### **View**

* Activity ve XML dosyaları
* Kullanıcı arayüzünü temsil eder

### **ViewModel**

* UI ile veri katmanı arasındaki bağlantıyı sağlar
* UseCase katmanı üzerinden veriyi alır
* LiveData kullanarak UI state yönetimi yapar

---

## 🧩 Mimari Katmanlar

* **data** → Entity, DAO, Database, Repository
* **domain** → UseCase’ler (iş kuralları)
* **ui** → Activity, Adapter
* **viewmodel** → ViewModel & Factory sınıfları
* **util** → Resource sınıfı (Loading / Success / Error state)

---

## 🎯 Amaç

Bu proje ile:

* Room Database kullanımını
* MVVM ve Clean Architecture mantığını
* ViewModel & LiveData yapısını
* Filtreleme ve state yönetimini
* Modern Android UI geliştirme yaklaşımlarını

daha iyi anlamak ve uygulamak hedeflenmiştir.

---

## 📌 Not

Bu proje **kişisel öğrenme ve pratik amaçlı** geliştirilmiştir.

