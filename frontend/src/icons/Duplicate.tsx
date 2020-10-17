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

export const Duplicate = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M5.86665 0H22.9333C23.2162 0 23.4875 0.112381 23.6876 0.312419C23.8876 0.512458 24 0.783769 24 1.06667V18.1333C24 18.4162 23.8876 18.6875 23.6876 18.8876C23.4875 19.0876 23.2162 19.2 22.9333 19.2H5.86665C5.58376 19.2 5.31245 19.0876 5.11241 18.8876C4.91237 18.6875 4.79999 18.4162 4.79999 18.1333V1.06667C4.79999 0.783769 4.91237 0.512458 5.11241 0.312419C5.31245 0.112381 5.58376 0 5.86665 0ZM6.93332 2.13333V17.0667H21.8667V2.13333H6.93332Z"></path>
      <path d="M1.06667 4.79999H18.1333C18.4162 4.79999 18.6875 4.91237 18.8876 5.11241C19.0876 5.31245 19.2 5.58376 19.2 5.86665V22.9333C19.2 23.2162 19.0876 23.4875 18.8876 23.6876C18.6875 23.8876 18.4162 24 18.1333 24H1.06667C0.783769 24 0.512458 23.8876 0.312419 23.6876C0.112381 23.4875 0 23.2162 0 22.9333V5.86665C0 5.58376 0.112381 5.31245 0.312419 5.11241C0.512458 4.91237 0.783769 4.79999 1.06667 4.79999V4.79999Z"></path>
      <path d="M8.57142 13.3714V7.20001H10.6286V13.3714H16.8V15.4286H10.6286V21.6H8.57142V15.4286H2.39999V13.3714H8.57142Z"></path>
    </svg>
  );
};
Duplicate.propTypes = propTypes;
