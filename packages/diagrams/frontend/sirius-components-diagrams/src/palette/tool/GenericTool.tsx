/*******************************************************************************
 * Copyright (c) 2020, 2022 Remix Design Studio, Obeo and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     Remix Design Studio - initial implementation as SVG
 *     Obeo - conversion into JSX and adaptation
 *******************************************************************************/

export const GenericTool = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 16 16"
      width="16"
      height="16"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M 8,15 C 4.1339,15 1,11.8661 1,8 1,4.1339 4.1339,1 8,1 c 3.8661,0 7,3.1339 7,7 0,3.8661 -3.1339,7 -7,7 z M 8,13.6 A 5.6,5.6 0 1 0 8,2.4 5.6,5.6 0 0 0 8,13.6 Z M 8,4.535 11.465,8 8,11.465 4.535,8 Z M 8,6.5153 6.5153,8 8,9.4847 9.4847,8 Z" />
    </svg>
  );
};
<svg xmlns="http://www.w3.org/2000/svg"></svg>;
