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
  resetLabelStyleProperties: (labelId: string, propertiesToReset: string[]) => void;
  resetNodeStyleProperties: (propertiesToReset: string[]) => void;
  resetInsideLabelBold: (labelId: string) => void;
  resetBackground: () => void;
}

export const GQLResetNodeAppearanceMutation = gql`
  mutation resetNodeAppearance($input: ResetNodeAppearanceInput!) {
    resetNodeAppearance(input: $input) {
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

export interface GQLResetNodeApparenceData {
  resetNodeAppearance: GQLResetNodeApparencePayload;
}

export type GQLResetNodeApparencePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLResetNodeAppearanceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  nodeId: string;
  propertiesToReset: string[];
}

export interface GQLResetNodeApparenceVariables {
  input: GQLResetNodeAppearanceInput;
}

export const GQLResetLabelAppearanceMutation = gql`
  mutation resetLabelAppearance($input: ResetLabelAppearanceInput!) {
    resetLabelAppearance(input: $input) {
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

export interface GQLResetLabelApparenceData {
  resetLabelAppearance: GQLResetLabelApparencePayload;
}

export type GQLResetLabelApparencePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLResetLabelAppearanceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  ownerElementId: string;
  labelId: string;
  propertiesToReset: string[];
}

export interface GQLResetLabelApparenceVariables {
  input: GQLResetLabelAppearanceInput;
}
