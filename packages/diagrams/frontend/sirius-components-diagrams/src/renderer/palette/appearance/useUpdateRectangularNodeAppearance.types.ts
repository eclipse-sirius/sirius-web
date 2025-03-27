/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { gql } from '@apollo/client';
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export const GQLEditRectangularNodeAppearanceMutation = gql`
  mutation editRectangularNodeAppearance($input: EditRectangularNodeAppearanceInput!) {
    editRectangularNodeAppearance(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export interface GQLEditRectangularNodeApparenceData {
  editRectangularNodeAppearance: GQLEditRectangularNodeApparencePayload;
}

export type GQLEditRectangularNodeApparencePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLEditRectangularNodeApparenceVariables {
  input: GQLEditRectangularNodeApparenceInput;
}

export interface GQLEditRectangularNodeApparenceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  nodeId: string;
  appearance: Partial<GQLRectangularNodeAppearanceInput>;
}

export interface GQLRectangularNodeAppearanceInput {
  background: string;
  insideLabel: Partial<GQLLabelAppearanceInput> & Required<{ labelId: string }>;
}

export interface GQLLabelAppearanceInput {
  bold: boolean;
}

export interface useUpdateRectangularNodeAppearanceValue {
  updateInsideLabelBold: (labelId: string, isBold: boolean) => void;
  updateBackground: (background: string) => void;
  resetInsideLabelBold: (labelId: string, currentIsBold: boolean) => void;
  resetBackground: (currentBackground: string) => void;
}

export const GQLResetRectangularNodeAppearanceMutation = gql`
  mutation resetRectangularNodeAppearance($input: ResetRectangularNodeAppearanceInput!) {
    resetRectangularNodeAppearance(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export interface GQLResetRectangularNodeApparenceData {
  resetRectangularNodeAppearance: GQLResetRectangularNodeApparencePayload;
}

export type GQLResetRectangularNodeApparencePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLResetRectangularNodeApparenceVariables {
  input: GQLEditRectangularNodeApparenceInput;
}
