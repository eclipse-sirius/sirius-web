/*******************************************************************************
 * Copyright (c) 2020 Remix Design Studio, Obeo and others
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
 *     Obeo - conversion into JSX
 *******************************************************************************/
import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  title: PropTypes.string.isRequired,
};
export const NewDocument = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 23 25"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}
    >
      <title>{title}</title>
      <path d="M10.2222 1.25C10.2222 0.56 9.64978 0 8.94444 0H1.27778C0.572444 0 0 0.56 0 1.25V6.25C0 6.94 0.572444 7.5 1.27778 7.5H3.83333V21.25C3.83333 21.94 4.40578 22.5 5.11111 22.5H12.7778V23.75C12.7778 24.44 13.3502 25 14.0556 25H21.7222C22.4276 25 23 24.44 23 23.75V18.75C23 18.06 22.4276 17.5 21.7222 17.5H14.0556C13.3502 17.5 12.7778 18.06 12.7778 18.75V20H6.38889V12.5H12.7778V13.75C12.7778 14.44 13.3502 15 14.0556 15H21.7222C22.4276 15 23 14.44 23 13.75V8.75C23 8.06 22.4276 7.5 21.7222 7.5H14.0556C13.3502 7.5 12.7778 8.06 12.7778 8.75V10H6.38889V7.5H8.94444C9.64978 7.5 10.2222 6.94 10.2222 6.25V1.25Z"></path>
      <path d="M17 21.5H22" stroke="white"></path>
      <line x1="19.4166" y1="18.9917" x2="19.4999" y2="23.9917" stroke="white"></line>
    </svg>
  );
};

NewDocument.propTypes = propTypes;
