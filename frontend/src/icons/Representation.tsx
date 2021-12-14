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

export const Representation = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}
    >
      <title>{title}</title>
      <path d="M16 0C16.736 0 17.3333 0.597333 17.3333 1.33333V6.66667C17.3333 7.40267 16.736 8 16 8H13.3333V10.6667H18.6667C19.4027 10.6667 20 11.264 20 12V16H22.6667C23.4027 16 24 16.5973 24 17.3333V22.6667C24 23.4027 23.4027 24 22.6667 24H14.6667C13.9307 24 13.3333 23.4027 13.3333 22.6667V17.3333C13.3333 16.5973 13.9307 16 14.6667 16H17.3333V13.3333H6.66667V16H9.33333C10.0693 16 10.6667 16.5973 10.6667 17.3333V22.6667C10.6667 23.4027 10.0693 24 9.33333 24H1.33333C0.597333 24 0 23.4027 0 22.6667V17.3333C0 16.5973 0.597333 16 1.33333 16H4V12C4 11.264 4.59733 10.6667 5.33333 10.6667H10.6667V8H8C7.264 8 6.66667 7.40267 6.66667 6.66667V1.33333C6.66667 0.597333 7.264 0 8 0H16Z"></path>
    </svg>
  );
};
Representation.propTypes = propTypes;
