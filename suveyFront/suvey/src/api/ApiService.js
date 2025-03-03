import React from "react";

const API_BASE_URL = "http://localhost:8080";

export async function call(api, method, request) {
    let headers = new Headers({
        "Content-Type": "application/json",
    });

    // 로컬 스토리지에서 ACCESS TOKEN 가져오기
    const accessToken = localStorage.getItem("ACCESS_TOKEN");
    if (accessToken && accessToken !== null) {
        headers.append("Authorization", "Bearer " + accessToken);
    }

    let options = {
        headers: headers,
        url: API_BASE_URL + api,
        method: method,
    };

    if (request) {
        options.body = JSON.stringify(request);
    }

    try {
        const response = await fetch(options.url, options);
        let result = { status: response.status }; // 항상 status 필드를 포함
        console.log("Http status : " + response.status);

        if (response.ok) {
            try {
                const text = await response.text(); // 응답 body 가져오기
                if (text) {
                    const parsedData = JSON.parse(text); // JSON 파싱
                    Object.assign(result, parsedData); // result에 데이터 병합
                }
            } catch (e) {
                throw new Error("JSON 파싱 실패: " + e.message);
            }
        } else if (response.status === 401 || response.status === 403) {
            window.location.href = "/signin"; // redirect
        } else {
            const errorData = await response.text(); // 에러 메시지 가져오기
            result.error = `HTTP 에러 발생: ${errorData}`;
        }

        return result;

    } catch (error) {
        console.error("HTTP 요청 중 오류 발생:", error);
        throw error; // 에러를 호출자에게 전달
    }
}



export function signin(userDTO) {
    return call("/auth/signin","POST", userDTO)
    .then((response) => {
        console.log("Api SignIn : " + response);

        if(response.status === 400){
            return response;
        }
        else if(response.token){
            console.log(response.token);
            localStorage.setItem("ACCESS_TOKEN", response.token);
            return response;

        }
    })
 }

export function signout() {
    localStorage.setItem("ACCESS_TOKEN",null);
    window.location.href = "/signin";
}

export function signup(userDTO){
    return call("/auth/signup","POST",userDTO)
}

export function writeContent(){
    window.location.href = "/write";
}

export function checkNickname(nickname) {
    return call(`/auth/nicknameCheck?nickname=${nickname}`,"GET",null);
}

export function saveLink(link) {
    return call("/suvey","POST",link);
}

export function deleteLink(item) {
    return call("/suvey","DELETE",item);
}

export function deleteGroup(groupDTO) {
    console.log(groupDTO);
    return call("/suvey/group","DELETE",groupDTO);
}

export function saveNote(item){
    return call("/suvey","PUT",item);
}

export function updateGroupTitle(item){
    return call("/suvey/group","PUT",item);
}

export function search(option, query) {
    // option에 따라 다른 필드에서 검색
    return call(`/suvey/group/search?field=${option}&query=${query}`, "GET", null);
  }
  
export function sendCodeToEmail(email){
    return call(`/auth/emails/verification-requests?email=${email}`,"POST",null);
}

export function verificationEmail(email, code){
    return call(`/auth/emails/verifications?email=${email}&code=${code}`,"POST",null);
}



// UserInfoController 관련 API
export function sendMyInfo(userInfoDTO){
    return call("/info","POST",userInfoDTO);
}
