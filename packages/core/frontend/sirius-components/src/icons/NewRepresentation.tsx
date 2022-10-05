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
 *     Obeo - conversion into JSX
 *******************************************************************************/
import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  title: PropTypes.string.isRequired,
};

export const NewRepresentation = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M17.3333 1.33333C17.3333 0.597333 16.736 0 16 0H8C7.264 0 6.66667 0.597333 6.66667 1.33333V6.66667C6.66667 7.40267 7.264 8 8 8H10.6667V10.6667H5.33333C4.59733 10.6667 4 11.264 4 12V16H1.33333C0.597333 16 0 16.5973 0 17.3333V22.6667C0 23.4027 0.597333 24 1.33333 24H9.33333C10.0693 24 10.6667 23.4027 10.6667 22.6667V17.3333C10.6667 16.5973 10.0693 16 9.33333 16H6.66667V13.3333H17.3333V16H14.6667C13.9307 16 13.3333 16.5973 13.3333 17.3333V22.6667C13.3333 23.4027 13.9307 24 14.6667 24H22.6667C23.4027 24 24 23.4027 24 22.6667V17.3333C24 16.5973 23.4027 16 22.6667 16H20V12C20 11.264 19.4027 10.6667 18.6667 10.6667H13.3333V8H16C16.736 8 17.3333 7.40267 17.3333 6.66667V1.33333Z"></path>
      <path d="M18 20.5H23" stroke="white"></path>
      <path d="M20.5 18V23" stroke="white"></path>
    </svg>
  );
};
NewRepresentation.propTypes = propTypes;
