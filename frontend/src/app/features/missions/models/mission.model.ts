export type MissionType =
  | 'INTERNAL' | 'EVA' | 'SCIENCE' | 'LOGISTICS'
  | 'MAINTENANCE' | 'RESCUE' | 'INSPECTION' | 'EXPLORATION' | 'TRANSPORT';

export type MissionPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
export type MissionSafetyLevel = 'STANDARD' | 'ELEVATED' | 'HIGH_RISK' | 'CRITICAL';
export type MissionStatus = 'DRAFT';

// Il "code" NON è più qui: lo genera e assegna il backend alla creazione.
export interface MissionCreateRequest {
  title: string;
  description?: string;
  missionType: MissionType;
  idOriginColony: number;
  idDestinationColony?: number | null;
  destinationName?: string;
  plannedStartAt: string;
  plannedEndAt: string;
  priority: MissionPriority;
  safetyLevel: MissionSafetyLevel;
}

// Cosa torna indietro il backend dopo la creazione (con code e status assegnati)
export interface MissionCreateResponse {
  idMission: number;
  code: string;
  status: MissionStatus;
  title: string;
  createdAt: string;
}
