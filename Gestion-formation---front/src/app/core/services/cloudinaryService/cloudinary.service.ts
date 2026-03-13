// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { API_BASE_URL } from 'src/app/shared/utils/constants';

// @Injectable({
//   providedIn: 'root'
// })
// export class CloudinaryService {

//   private apiUrl = `${API_BASE_URL}/api/v1/cloudinary`;

//   constructor(private http: HttpClient) {}

//   uploadFiles(files: File[]): Observable<{ urls: string[] }> {
//     const formData = new FormData();
//     files.forEach(file => {
//       formData.append('files', file);
//     });
//     return this.http.post<{ urls: string[] }>(`${this.apiUrl}/upload`, formData);
//   }

//   deleteFile(publicId: string): Observable<{ message: string }> {
//     return this.http.delete<{ message: string }>(`${this.apiUrl}/${publicId}`);
//   }
// }
