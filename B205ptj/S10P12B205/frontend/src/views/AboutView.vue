<script setup>


</script>

<template>
    <v-app class="bg">
    <v-container>
      <v-row>
        <v-container id="text1">
          <p>
            Talk? 톡? 토크?
          </p>
          <br>
          <p>
            세상에서 하찮은 말은 없다.
          </p>
          <p>
            시시콜콜한 이야기, 가벼운 이야기를 이곳에서 나눠보세요.
          </p>
        </v-container>
      </v-row>
      <v-row style="align-content: center;" class="d-flex">
        <v-col
          cols="5"
          offset="1"
        >
          <div id="text2">

            <p>
              토키는 토끼와 토크(talk) 의 합성어 입니다.
            </p>
            <br>
            <p>
              토끼 처럼 자유롭게 뛰놀며,
            </p>
            <p>
              대화를 나누는 모임입니다.
            </p>
            <p>
              이곳에서 자유롭게 뛰다 가세요.
            </p>
          </div>
        </v-col>
        <v-col
          cols="1"
          offset="5"
        >
          <img
            id="toki-image" 
            src="@/assets/profile_assets/캡처17.png">
        </v-col>
      </v-row>

    </v-container>
  </v-app>
</template>


<style scoped>

.bg {
  background: linear-gradient(180deg, #E0F5FF 0%, rgba(224, 245, 255, 0) 88.94%);
  background-size: cover;
  background-position: center bottom;
  background-image: url('@/assets/profile_assets/Footer.png');
  width: 100%;
  height: 100%;
  position: absolute;
  bottom: 0;
  margin-bottom: 10px;
}


#bg-image {
  background-image: url('@/assets/profile_assets/vecteezy_sea-waves-clipart-design-illustration_9379755.png');
  display: fiex;
  background-size: cover;
  margin-top: 20%;
  width: 100%;
  text-align: center;
}

#text1 {
  position: absolute;
  top: 5%;
  
  
  font-family: 'Warhaven';
  font-style: normal;
  font-weight: 700;
  font-size: 33px;
  line-height: 50px;
  text-align: center;
  
  color: #000000;
}

#text2 {
  white-space: nowrap;
  position: absolute;
  left: 2.33%;
  right: 54.38%;
  top: 40.97%;
  bottom: 25.05%;

  font-family: 'Gothic A1';
  font-style: normal;
  font-weight: 400;
  font-size: 25px;
  line-height: 50px;
  text-align: center;

  color: #000000;
}

#toki-image {
  position: absolute;
  top: 40.97%;
  left: 65%;
  
  
  
  height: 300px; 
  opacity: 70%; 
  border-radius: 11%;  
}

</style>